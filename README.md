
docker run -d --name nats -p 4222:4222 nats 

docker run -d --name mongodb  -p 27017:27017 -v $HOME/data:/data/db mongo:3

POST /crearJuego
{
    "kilometros": 3,
    "jugadores": {
        "112233": "Camilo andres",
        "4455443": "Pedro",
        "fffff": "Santiago"
    }
}


POST /iniciarJuego
{
    "juegoId": {
        "uuid": "a29bbc82-d462-431b-bcdb-df9565ae0199"
    }
}
