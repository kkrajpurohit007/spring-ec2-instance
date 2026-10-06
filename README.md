# Demo API

A small Spring Boot service for trying out EC2 targets and an AWS load balancer.

## Run locally

```sh
./mvnw spring-boot:run
```

The app listens on port `8080` by default:

- `GET http://localhost:8080/api/hello` returns the service name, instance ID, and a greeting.
- `GET http://localhost:8080/api/health` returns an `UP` status for the load balancer health check.

Set a different `INSTANCE_ID` on each running copy to see which instance handles a request:

```sh
INSTANCE_ID=instance-a ./mvnw spring-boot:run
```

## Try with an AWS load balancer

Run the app on each EC2 target, set a unique `INSTANCE_ID` per target, then register the targets with the load balancer's target group. Configure the target group's health-check path as `/api/health` on port `8080` and allow inbound traffic to port `8080` from the load balancer's security group.
