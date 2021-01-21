#!/bin/bash

sudo su
sudo yum update -y
sudo amazon-linux-extras install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
sudo chkconfig docker on
sudo yum install -y git
sudo curl -L https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo git clone https://github.com/nikseredinskiy/tweet-api.git
sudo git clone https://github.com/nikseredinskiy/tweet-ui.git
cd tweet-api/docker
sudo bash get-ip.sh
docker-compose up