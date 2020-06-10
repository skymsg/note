from base_runner:1
RUN apt-get install -y --no-install-recommends python3.7 ca-certificates\
	&& ln -s /usr/bin/python3.7 /usr/bin/python3  \
	&& ln -s /usr/bin/python3.7 /usr/bin/python  \
	&& curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py \
	&& python3 get-pip.py \
	&& pip3 install --upgrade pip setuptools \
	&& apt-get clean
