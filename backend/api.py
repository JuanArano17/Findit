# Archivo: app.py
from fastapi import FastAPI, UploadFile, File, Form
from fastapi.responses import JSONResponse
import uvicorn
import cv2
import numpy as np
import random
import os
from recognition_model import load_yolov8_model
from data_collection import save_image_for_training

HOUSE_ITEMS = [
    'backpack', 'umbrella', 'handbag', 'tie', 'suitcase', 'bottle',
    'wine glass', 'cup', 'fork', 'knife', 'spoon', 'bowl', 'banana',
    'apple', 'sandwich', 'orange', 'broccoli', 'carrot', 'hot dog',
    'pizza', 'donut', 'cake', 'chair', 'couch', 'potted plant', 'bed',
    'dining table', 'toilet', 'tv', 'laptop', 'mouse', 'remote',
    'keyboard', 'cell phone', 'microwave', 'oven', 'toaster', 'sink',
    'refrigerator', 'book', 'clock', 'vase', 'scissors', 'teddy bear',
    'hair drier', 'toothbrush'
]

app = FastAPI()

# Dictionary to store specific models for trained objects
trained_models = {}

def load_appropriate_model(word: str):
    """Load fine-tuned model if available, otherwise use default model"""
    if word not in trained_models:
        model_path = f"models/findit_{word}.pt"
        if os.path.exists(model_path):
            trained_models[word] = load_yolov8_model(model_path)
        else:
            trained_models[word] = load_yolov8_model("yolov8x.pt")
    return trained_models[word]

# Variable global para rastrear el último objeto devuelto
last_object = None

@app.post("/detection")
async def detect_object(
    word: str = Form(...), 
    file: UploadFile = File(...)
):
    """Endpoint para enviar una imagen JPEG y una palabra a detectar en la imagen."""
    # Use appropriate model for the word
    model = load_appropriate_model(word.lower())

    # Leer el archivo de imagen y convertirlo en un array de OpenCV
    contents = await file.read()
    nparr = np.frombuffer(contents, np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    if img is None:
        return JSONResponse(status_code=400, content={"error": "Invalid image file."})
    
    result = model.predict(img, conf=0.25)
    detected = False
    confidence = 0.0
    
    try:
        # Procesar el primer resultado para una predicción estática
        if result and hasattr(result[0], 'boxes') and result[0].boxes is not None:
            for i, cls in enumerate(result[0].boxes.cls):
                if model.names[int(cls)].lower() == word.lower():
                    detected = True
                    confidence = float(result[0].boxes.conf[i])
                    print(f"Detected {model.names[int(cls)]} with confidence {confidence}")
                    # Si la confianza es mayor al 50%, guardamos la imagen para entrenamiento
                    if confidence >= 0.5:
                        save_image_for_training(img, word.lower())
                    break
    except Exception as e:
        return JSONResponse(status_code=500, content={"error": str(e)})
    
    return {
        "detected": detected,
        "confidence": float(confidence),
        "saved_for_training": confidence >= 0.5
    }

@app.get("/object")
async def get_object():
    """Endpoint para obtener una palabra aleatoria sin repetir la última."""
    global last_object
    random_object = random.choice(HOUSE_ITEMS)
    while random_object == last_object:
        random_object = random.choice(HOUSE_ITEMS)
    last_object = random_object
    return {"word": random_object}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
