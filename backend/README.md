# FindIt - The Object Hunt Game API

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Execution](#execution)
- [API Endpoints](#api-endpoints)
- [Built With](#built-with)
- [Authors](#authors)
- [License](#license)

## Introduction

FindIt now provides an API that processes images on demand. Users can send an image and a word to check if the object is detected in the image and can request a random target object from the model's classes.

## Features

- **Object Detection API:** Send an image and a word to verify if the word appears in the detection result.
- **Random Object Endpoint:** Get one random object name that the model can detect.
- **Powered by YOLOv8:** Utilizes the advanced YOLOv8 model for object detection.

## Getting Started

### Prerequisites

Ensure you have the following installed:

```
Python 3.9
PyTorch
Ultralytics
FastAPI
uvicorn
```

### Installation

Clone the repository and install the required dependencies:

```
pip install -r requirements.txt
pip install fastapi uvicorn
```

### Execution

Run the main.py file or run the following line in bash:

```
uvicorn main:app --host 0.0.0.0 --port 8000 --timeout-keep-alive 60
```

## API Endpoints

### 1. Detection Endpoint

Endpoint: `/detection` (GET)

Parameters:
- word: The target object name.
- file: The image file (.jpg, etc.) to be processed.

Example (using cURL):
```
curl -X 'GET' \
  'http://127.0.0.1:8000/detection?word=cellphone' \
  -F 'file=@/path/to/your/image.jpg'
```
Returns:
```
{"detected": true}
```

### 2. Get Object Endpoint

Endpoint: `/object` (GET)

Returns a random object name (word) that can be detected.

Example (using cURL):
```
curl -X 'GET' 'http://127.0.0.1:8000/object'
```
Returns:
```
{"word": "cellphone"}
```

## Built With

- [FastAPI](https://fastapi.tiangolo.com/) - The web framework used.
- [Ultralytics](https://ultralytics.com/) - Used for object detection models.
- [Python](https://www.python.org/) - The programming language used for backend development.

## Authors

- **Juan Pablo Arano** - *Initial work* - [Juan Arano](https://github.com/JuanArano17)
- **Sofia Alejandra Prieto** - *Feature Implementation* - [Sofia Prieto](https://github.com/SofiaPrieto)
- **Sebastian Portillo** - *Testing and QA* - [Sebastian Portillo](https://github.com/SebaPortill0)
- **Gianluca Zinni** - *Documentation* - [Gianluca Zinni](https://github.com/GianlucaZinni)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
