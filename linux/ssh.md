# ssh
## generating a new ssh key
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
## ssh-copy-id
ssh-copy-id user@host
## add existing ssh key pair
sudo chmod 666 ~/.ssh/id_rsa
ssh-add
ssh-add -l
