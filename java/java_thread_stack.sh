# get the java process pid
ps -ef|grep "java process name"
# list the thread of this process
ps -mp 'pid' -o THREAD,tid,time
# show the hex format of the tid
printf "%x\n" 'tid'
# get the thread stack of this process 
jstack 'pid'> thread_stack.log
# use vim open the thread stack log , use  locate the thread
vim thread_stack.log