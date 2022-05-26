
docker run -d --name nats -p 4222:4222 nats 

docker run -d --name mongodb  -p 27017:27017 -v $HOME/data:/data/db mongo:3

`
curl -X POST localhost:8080/crearJuego -H 'Content-Type: application/json' -d '{ "kilometros": 3, "juegoId":"xyz", "jugadores": { "112233": "Camilo andres", "4455443": "Pedro", "fffff": "Santiago" } }'
`

`
curl -X POST localhost:8080/iniciarJuego -H 'Content-Type: application/json' -d '{ "juegoId":"xyz" }'
`