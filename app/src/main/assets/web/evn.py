import json
from datetime import datetime

f = open('evn.json').read()

evns = json.loads(f)

evns2 = {}
keys_s = list(evns.keys())
keys_s .sort()
for t in keys_s:
    dt = datetime.fromtimestamp(int(t[:-3]))
    dt = str(dt)
    evns2[dt] = evns[t]

f = open('evn2.json', 'w')
f.write(json.dumps(evns2, indent=4))
