# FindIt - The Object Hunt Game API

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Running the Application](#running-the-application)
  - [Starting the API](#starting-the-api)
  - [Training Custom Models](#training-custom-models)
- [API Endpoints](#api-endpoints)
- [Built With](#built-with)
- [Authors](#authors)
- [License](#license)

## Introduction

FindIt is an object detection game API that processes images on demand. Users can send an image and a word to check if the object is detected in the image and can request random target objects from the model's classes.

## Features

- Object Detection API with YOLOv8
- Random object selection
- Custom model training support
- Automatic data collection for model improvement

## Getting Started

### Prerequisites

- Python 3.9 or higher
- CUDA-capable GPU (recommended for training)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/JuanArano17/Findit.git
cd Findit/backend
```

2. Install dependencies:
```bash
pip install -r requirements.txt
```

3. Download the base YOLOv8 model:
```bash
wget https://github.com/ultralytics/assets/releases/download/v0.0.0/yolov8x.pt
```

## Running the Application

### Starting the API

1. Navigate to the backend directory:
```bash
cd backend
```

2. Run the API server:
```bash
python main.py
```

The API will start on http://localhost:8000

### Training Custom Models

1. Prepare your training data in the following structure:
```
backend/
└── training/
    └── object_name/
        ├── train/
        │   ├── images/
        │   └── labels/
        └── test/
            ├── images/
            └── labels/
```

2. Run the training script:
```bash
python train.py
```

3. Follow the prompts:
- Enter the object name when prompted
- Wait for training to complete
- The trained model will be saved in `models/findit_objectname.pt`

## API Endpoints

### Detection Endpoint

POST `/detection`
```bash
curl -X POST \
  'http://localhost:8000/detection' \
  -F 'word=bottle' \
  -F 'file=@/path/to/image.jpg'
```

### Random Object Endpoint

GET `/object`
```bash
curl -X GET 'http://localhost:8000/object'
```

## Built With

- FastAPI - Web framework
- Ultralytics YOLOv8 - Object detection
- PyTorch - Deep learning framework

## Authors

- **Juan Pablo Arano** - [Juan Arano](https://github.com/JuanArano17)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
