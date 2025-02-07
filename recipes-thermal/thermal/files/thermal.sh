#!/bin/sh

for temp in /sys/class/thermal/*/temp; do
    if [ -f "$temp" ]; then
        value=$(cat "$temp")
        echo "$(($value / 1000))°C"
    fi
done
