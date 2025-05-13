# FindIt - The Object Hunt Game

## Table of Contents
- [Ejecución](#ejecucion)

## Ejecución

Para ejecutar el juego corriendo en un celular ambos tienen que estar conectados a la misma red. Una vez hecho esto, primero correr el servidor en la carpeta backend con la siguiente linea:

```
uvicorn main:app --host 0.0.0.0 --port 8000 --timeout-keep-alive 60
```

Luego, en el frontend, cambiar la linea 13 del archivo findit/ui/data/game/ApiClient por

```
private const val BASE_URL = "http://<hostname>:8000/"
```

donde <hostname> es el nombre o dirección IPv4 de la computadora corriendo el servidor en la red Wifi. Y tambien agregar la siguiente linea al archivo res/xml/network_security_config.xml:

```
<domain includeSubdomains="true"><hostname></domain>
```

nuevamente, el <hostname> es el mismo valor que antes.

Luego, correr la aplicacion en un celular conectado con USB a la computadora.