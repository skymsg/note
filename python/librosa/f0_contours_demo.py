import matplotlib.pyplot as plt
import librosa
import numpy as np

file_name = "/mnt/d/workspace/aigc/日语作业.wav"
y, sr = librosa.load(file_name)
f0 = librosa.yin(y,fmin=80,fmax=400)
f0[np.isnan(f0)]=0
times = librosa.times_like(f0)
plt.plot(times,f0,"_",linewidth=1)
plt.xlabel("Times(s)")
plt.ylabel("F0")
plt.show()
print("f0 extracted!")
