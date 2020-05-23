
async def handle(request):
    name = request.
    text = "Hello, " + nam
    return web.Response(text=text)

app = web.Application()
app.add_routes([web.get('/hello', handle),
                web.get('/{name}', handle)])

if __name__ == '__main__':
    web.run_app(app)
