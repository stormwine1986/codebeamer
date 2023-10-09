#!/bin/bash
chmod 600 /root/.ssh/id_ed25519
yum install jq -y
yum install git -y
yum install python3.11 -y
yum install python3-pip -y
pip3 install -r requirements.txt