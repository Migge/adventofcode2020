cat input_day2 | sed 's/-/ /;s/://' | awk '{ split($4,a,$3,s); if (length(s)>=$1 && length(s)<=$2) print }' | wc -l
