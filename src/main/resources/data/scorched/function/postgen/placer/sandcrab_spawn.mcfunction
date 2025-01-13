

execute as @s[tag=scorched_postgen.sandcrab1] run function scorched:mob/sandcrab/color/red
execute as @s[tag=scorched_postgen.sandcrab2] run function scorched:mob/sandcrab/color/yellow
execute as @s[tag=scorched_postgen.sandcrab3] run function scorched:mob/sandcrab/color/blue
execute as @s[tag=scorched_postgen.sandcrab4] run function scorched:mob/sandcrab/color/green
execute as @s[tag=scorched_postgen.sandcrab5] run function scorched:mob/sandcrab/color/magenta


execute as @s[tag=scorched_postgen.sandcrab1] run tellraw @a[tag=scorched_debug] [{"text": "[Scorched: Generated \"scorched:sandcrab\" (Red)]","color": "gray","italic": true}]
execute as @s[tag=scorched_postgen.sandcrab2] run tellraw @a[tag=scorched_debug] [{"text": "[Scorched: Generated \"scorched:sandcrab\" (Yellow)]","color": "gray","italic": true}]
execute as @s[tag=scorched_postgen.sandcrab3] run tellraw @a[tag=scorched_debug] [{"text": "[Scorched: Generated \"scorched:sandcrab\" (Blue)]","color": "gray","italic": true}]
execute as @s[tag=scorched_postgen.sandcrab4] run tellraw @a[tag=scorched_debug] [{"text": "[Scorched: Generated \"scorched:sandcrab\" (Green)]","color": "gray","italic": true}]
execute as @s[tag=scorched_postgen.sandcrab5] run tellraw @a[tag=scorched_debug] [{"text": "[Scorched: Generated \"scorched:sandcrab\" (Magenta)]","color": "gray","italic": true}]

kill @s[tag=scorched_postgen.sandcrab]