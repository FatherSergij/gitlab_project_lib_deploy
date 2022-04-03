kubectl delete -n ${BRANCH} secret regcred --ignore-not-found;
kubectl create secret docker-registry regcred
 --docker-server=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
 --docker-username=AWS
 --docker-password=$(aws ecr get-login-password --region ${AWS_REGION})
 --namespace=${BRANCH};
envsubst < service-nginx-phpfpm.yaml | kubectl apply -f -;
kubectl delete deploy deploy-nginx-phpfpm -n ${BRANCH};
envsubst < deploy-nginx-phpfpm.yaml | kubectl apply -f -
