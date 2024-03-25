# Trang tham khảo
[kioviet.vn](https://knv02.kiotviet.vn/man/#/login)

Tài khoản: 0335678564

Mật khẩu: 927933

# Làm việc với git
_Pull code từ nhánh master_
`git pull origin master`


# Làm việc với docker 
---
- DockerFile: chứa các lệnh chạy khi build images
- .dockerignore: chứa đường dẫn các file/folder sẽ bỏ qua khi build

# Build docker
- C1:
`docker build -t <project build name>:<phiên bản build>`
- C2:
Mở file DockerFile rồi ấn chuột phải và chọn Build image
- C3: ấn ctrl + shift + p -> gõ docker build image

## Chạy với tên container
`docker run --name <container name tùy chọn> -d -p <port build:port chạy> <project build name>:<phiên bản build>`
`vi dụ docker run --name docker-rest-api -d -p 4200:8080 docker-rest-api:1.0.0`

## Kiểm tra lịch sử của phiên bản ứng dụng
`docker history <project build name>:<phiên bản build>`

# Lấy các images
`docker images`

# dừng docker
`docker stop <container name>`

# Lấy danh sách log của ứng dụng trong đó -f là bắt buộc (forch)
`docker logs -f <container-name>`

# Thực hiện bash trên ứng dụng
`docker exec -it <container-name> bash`
# Nhập lệnh bash như ls -la
# thoát bash thì gõ `exit`

# Push lên docker hub
1. vào docker hub
2. Tạo repository
3. chạy lệnh
   docker tag local-image:tagname new-repo:tagname
   docker push new-repo:tagname

## Làm việc với ELK (elasticsearch + LogStash + kibana)
b1: Tạo docker network
`docker network create <network-name>`
b2: pull elasticsearch
`docker pull elasticsearch:8.4.3`
b3: chạy image với elastic
`docker run --name elk --net <network-name> -p 8080:8080 -p 8081:8081 -it elasticsearch:8.4.3`

Sau khi chạy sẽ sinh ra 
- password
- HTTP CA certificate SHA-256 fingerprint (key cho kibana)
- token cho kibana

