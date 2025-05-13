import requests
import matplotlib.pyplot as plt
import os

def test_endpoint(iterations=1024):
    url = "http://localhost:8000/detection"
    
    # Variables para almacenar los conteos de resultados True y False
    count_true = 0
    count_false = 0

    # Cargamos la imagen .jpeg en memoria usando la ruta relativa
    image_path = os.path.join(os.path.dirname(__file__), "test_image.jpeg")  # changed
    with open(image_path, "rb") as f:  # changed
        image_bytes = f.read()

    # Realizamos N iteraciones (por defecto 1024)
    for i in range(iterations):
        # Preparar los datos y el archivo para el request POST (multipart/form-data)
        files = {"file": ("b.jpeg", image_bytes, "image/jpeg")}
        data = {"word": "mouse"}

        # Enviar la solicitud POST al endpoint /detection
        response = requests.post(url, data=data, files=files)

        # Interpretar la respuesta como JSON y contar el valor de 'detected'
        try:
            json_response = response.json()
            if json_response.get("detected") is True:
                count_true += 1
            else:
                count_false += 1
        except Exception:
            # Si hay algún error de parseo o de otro tipo, lo consideramos como False
            count_false += 1
    
    # Crear un gráfico de barras con los resultados de conteo de True/False
    labels = ["True", "False"]
    values = [count_true, count_false]

    plt.bar(labels, values)
    plt.title("Resultados endpoint /detection (mouse / b.jpeg) en 1024 iteraciones")
    plt.xlabel("Valor de 'detected'")
    plt.ylabel("Cantidad")
    plt.show()

if __name__ == "__main__":
    test_endpoint()
