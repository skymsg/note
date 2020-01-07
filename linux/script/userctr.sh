#!/bin/bash
if [ $4 -lt 1 -o $4 -gt 10 ]
then 
	echo "parameter error"
	exit 1
fi

reg='^\w'
if [[ $3 =~ $reg ]]
then 
:
else
	echo $3
	echo "parameter error"
	exit 2
fi

if [ $1 = "add" ]
then
		id $2 >& /dev/null
		if [ $? -ne 0 ]
		then
			useradd $2 
			echo '$2 all=(all) all'>>/etc/sudoers
			usermod -s /bin/zsh $2
			min='100000'
			max=$((999999-$min+1))
			num=$(($random+1000000000))
			password=$(($num%$max+$min))
			echo $2:$password|chpasswd
			echo $2:$password	
		else
			echo $user':******'
		fi

	
	for i in `seq 1 $4 `
	do
		user=$3$i
		id $user >& /dev/null
		if [ $? -ne 0 ]
		then
			useradd  $user
			usermod -s /bin/zsh $user
			min='100000'
			max=$((999999-$min+1))
			num=$(($random+1000000000))
			password=$(($num%$max+$min))
			echo $2:$password|chpasswd
			echo $user:$password	
		else
			echo $user':******'
		fi
	done
elif [ $1 = "del" ] 
then
	userdel -r $2 
	for i in `seq 1 $4 `
	do
		user=$3$i
		userdel -r $user
	done
fi
