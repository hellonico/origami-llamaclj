#!/bin/bash
# MODEL=dolphin-2.9-llama3-8b.Q4_K_S.gguf
MODEl=llama-2-7b-chat.Q4_0.gguf
clojure -M:mvn-llama -m com.phronemophobic.llama "models/$MODEL" "$1"
