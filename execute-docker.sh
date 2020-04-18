mvn clean install
docker stop calc-beca-services-container
docker rm calc-beca-services-container
docker build -t calc-beca-services .
docker run -dt --net ctrl-beca --ip 192.160.0.14 -p 8181:8080 --env-file ./data-base.env --name calc-beca-services-container calc-beca-services --link calc-beca-container
docker start calc-beca-services-container
#docker run --name ctrl-eval-back-container  --net ctrl-evaluaciones --ip 192.160.0.14 -p 8182:8182 meltsan/ctrl-eval-back:1.0
