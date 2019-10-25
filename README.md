# Penny Wiser
Backend api microservice for pennywiser application.

# Getting Started

## Running Penny Wiser
Build from source:
```
git clone https://github.com/chenlu-chua/penny-wiser.git
cd penny-wiser
docker build -t pennywiser -f Dockerfile.dev .
docker run -p 8080:8080 pennywiser:latest
```
# Examples
```
curl -X GET \
  http://localhost:8080/api/v1/user/ \
```