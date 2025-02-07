#!/bin/sh

# Go to directory with thermal zones
cd /sys/class/thermal/

FIELD_WIDTH=20

# Iterate for each thermal_zone* directories
for zone in thermal_zone*; do
    # Checking that the $zone is a directory
    if [ -d "$zone" ]; then
        # Get zone name
        zone_name=$(cat "$zone/type")
        
        # Get temperature in 10^-3 part of degree and convert to degrees
        temp_mc=$(cat "$zone/temp")
        temp_c=$((temp_mc / 1000))
        
        # Output result
        # echo "$zone_name: Temp: $temp_c°C"
        printf "%-${FIELD_WIDTH}s: Temp: %d°C\n" "$zone_name" "$temp_c"
    fi
done
