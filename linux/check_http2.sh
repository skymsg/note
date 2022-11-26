domains=(
    www.shanghai.gov.cn
	www.baidu.com
	www.qq.com
	www.zhihu.com
)

for d in ${domains[@]}; do
    echo "Checking $d"
    curl -vso /dev/null --http2 https://$d 2>&1 | grep 'Using HTTP2'
    if [ $? -eq 0 ]; then
        echo "$d supports HTTP/2"
    else
        echo "$d does not support HTTP/2"
    fi
done
