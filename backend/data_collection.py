import os
import cv2
from datetime import datetime
import uuid

DATASET_DIR = "collected_images"

def save_image_for_training(img, object_name: str) -> str:
    """
    Guarda la imagen en una carpeta categorizada por el nombre del objeto.
    Returns: path where the image was saved
    """
    # Crear la estructura de carpetas si no existe
    folder_path = os.path.join(DATASET_DIR, object_name)
    os.makedirs(folder_path, exist_ok=True)

    # Generar nombre único para la imagen
    timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
    unique_id = uuid.uuid4().hex[:8]
    filename = f"{timestamp}_{unique_id}.jpg"
    
    # Guardar la imagen
    file_path = os.path.join(folder_path, filename)
    cv2.imwrite(file_path, img)
    
    return file_path