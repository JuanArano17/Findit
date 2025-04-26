import os
import yaml
from pathlib import Path
from recognition_model import train_yolov8_model
import shutil
from api import HOUSE_ITEMS

def prepare_training_data(training_dir: str, object_name: str) -> str:
    """Prepare training data and create data.yaml"""
    if not os.path.exists(os.path.join(training_dir, object_name, 'train')):
        raise ValueError(f"Training data for {object_name} not found in {training_dir}")
    
    # Create data.yaml configuration
    data = {
        'path': training_dir,
        'train': f'{object_name}/train',  # Direct path to train folder
        'val': f'{object_name}/test',     # Direct path to test folder
        'names': {i: item for i, item in enumerate(HOUSE_ITEMS)}  # Use HOUSE_ITEMS for consistent classes
    }
    
    # Save data.yaml
    yaml_path = os.path.join(training_dir, 'data.yaml')
    with open(yaml_path, 'w') as f:
        yaml.dump(data, f)
    
    return yaml_path

def display_results(results):
    """Display training results"""
    print("\nTraining Results:")
    print("-" * 50)
    print(f"mAP50-95: {results.maps[50]:.3f}")
    print(f"Precision: {results.results_dict['metrics/precision']:.3f}")
    print(f"Recall: {results.results_dict['metrics/recall']:.3f}")
    print("-" * 50)

def main():
    base_dir = Path(__file__).parent
    training_dir = base_dir / 'training'
    
    object_name = input("Enter object name to train (e.g., knife): ").lower()
    if object_name not in HOUSE_ITEMS:
        print(f"Warning: {object_name} is not in the standard HOUSE_ITEMS list.")
        print("This might affect model compatibility with the API.")
    
    # Prepare training data
    yaml_path = prepare_training_data(str(training_dir), object_name)
    
    # Train model
    base_model_path = base_dir / 'yolov8x.pt'
    results = train_yolov8_model(str(base_model_path), yaml_path)
    
    # Display results
    display_results(results)
    
    # Save the fine-tuned model to a specific location
    final_model_path = base_dir / 'models' / f'findit_{object_name}.pt'
    os.makedirs(os.path.dirname(final_model_path), exist_ok=True)
    shutil.copy2(base_dir / 'runs/detect/findit_training/weights/best.pt', final_model_path)
    print(f"\nFine-tuned model saved to: {final_model_path}")

if __name__ == "__main__":
    main()
