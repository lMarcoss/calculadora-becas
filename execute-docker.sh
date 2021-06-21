PATH_FILES_UPLOAD=$PATH_FILES_UPLOAD_UNSIS

docker stop calc-beca-services-container
docker rm -fv calc-beca-services-container
docker build -t calc-beca-services .

docker run -dt --net ctrl-beca --ip 192.160.0.14 -p 8181:8080 --env-file ./data-env.env -v $PATH_FILES_UPLOAD:/home --name calc-beca-services-container calc-beca-services --link calc-beca-container

docker start calc-beca-services-container
