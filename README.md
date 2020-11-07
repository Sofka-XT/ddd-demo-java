
docker run -d --name nats -p 4222:4222 nats 

docker run -d --name mongodb  -p 27017:27017 -v $HOME/data:/data/db mongo:3
