from flask import Flask
from flask import json
from flask import request
from redisUtil import RedisUtil
app = Flask(__name__)

@app.route('/hello')   
def hello():
    name = request.args.get('name', '')
    return json.jsonify({'msg':f"hello {name}"})

@app.route('/redis')   
def redis():
    key = request.args.get('key', '')
    res =RedisUtil.instance().get(key)
    return json.jsonify({'msg':f"{res}"})