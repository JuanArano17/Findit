from ultralytics import YOLO
import torch

def load_yolov5_model(model_path="yolov5s.pt"):
    """Loads YOLOv5 using torch.hub."""
    try:
        model = torch.hub.load('ultralytics/yolov5', 'yolov5s', pretrained=True)
    except Exception as e:
        raise Exception(f"Error loading YOLOv5 model: {e}")
    return model

def load_yolov8_model(model_path: str):
    """Load YOLOv8 model for inference"""
    return YOLO(model_path)

def train_yolov8_model(base_model_path: str, data_yaml_path: str, epochs: int = 10):
    """Train only the YOLOv8 head on new data"""
    model = YOLO(base_model_path)

    # Freeze all layers except the head
    for name, param in model.model.named_parameters():
        if 'head' not in name:
            param.requires_grad = False 

    # Train the model using the data.yaml configuration
    results = model.train(
        data=data_yaml_path,
        epochs=epochs,
        imgsz=100,
        batch=16,
        name='findit_training'
    )
    return results
