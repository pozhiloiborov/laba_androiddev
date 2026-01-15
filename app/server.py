from fastapi import FastAPI
import time
import random

app = FastAPI()

@app.get("/temp")
def get_temp():
    return {
        "t": round(random.uniform(20, 25), 1),
        "time": time.strftime("%H:%M:%S")
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)