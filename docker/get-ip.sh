#!/usr/bin/env bash

cat << EOF > .env
IP = $(curl http://169.254.169.254/latest/meta-data/public-ipv4)
EOF