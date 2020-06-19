# CalculadoraBecasBack
Servicios para administración de porcentaje de becas UNSIS

## Documentación en Swagger
* SERVER:PORT/calc-becas/docs/swagger-ui.html#/


## Compile and run
1. set environment
```export PATH_FILES_UPLOAD_UNSIS=/opt/unsis-app-becas-upload```
2. Asegurar que la variable tiene el valor asignado
```echo $PATH_FILES_UPLOAD_UNSIS```
3. exceute sh to build and deploy app
```./execute-docker.sh```

## Libraries
* Lombok