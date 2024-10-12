from datetime import datetime
from pydantic import BaseModel, PositiveInt


class User(BaseModel):
    id: int
    name: str = "John Doe"
    signup_ts: datetime | None
    tastes: dict[str, PositiveInt]

external_data = {

        "id": 3,
        "name": "jj",
        "signup_ts": '2024-07-22 12:22:33',
        "tastes":{
            "wine":9,
            "cheese":2,
            "cabbage":3

        }
}

user:User = User(**external_data)

print(user.id)

print(user.model_dump())
print(user.model_dump_json())

type_test:User = User(id="3",name="x2",signup_ts='2022-12-22 22:03:33',tastes={"xx":3})


external_data_error = {

        "id": "x3",
        "name": "jj",
        "signup_ts": 'x2024-07-22 12:22:33',
        "tastes":None
}

user_error:User = User(**external_data_error)