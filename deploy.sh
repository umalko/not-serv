docker build -t mavsvl/extension-client:latest -t mavsvl/extension-client:$SHA -f ./client/Dockerfile.dev ./client
docker build -t mavsvl/extension-server:latest -t mavsvl/extension-server:$SHA -f ./server/Dockerfile.dev ./server

docker push mavsvl/extension-client:latest
docker push mavsvl/extension-server:latest

docker push mavsvl/extension-client:$SHA
docker push mavsvl/extension-server:$SHA

kubectl apply -f k8s

kubectl set image deployments/server-deployment server=mavsvl/extension-server:$SHA
kubectl set image deployments/client-deployment client=mavsvl/extension-client:$SHA