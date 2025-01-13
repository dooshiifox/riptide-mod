
execute as @s[tag=scorched_temple_altar] run function scorched:structure/badlands/altar/altar
execute as @s[tag=scorched_temple_frames] run function scorched:structure/badlands/frame/frame
execute as @s[tag=scorched_temple_totem] run function scorched:structure/badlands/totem

execute as @s[tag=scorched_des_frames] run function scorched:structure/desert/frame/frame
execute as @s[tag=scorched_des_magic] run function scorched:structure/desert/magic_brick/main
execute as @s[tag=scorched_des_spawner] run function scorched:structure/desert/spawner/main

execute as @s[tag=scorched_quicktnt] if block ~ ~ ~ #scorched:block unless block ~ ~ ~ tnt run function scorched:structure/quicktnt
execute as @s[tag=scorched_permafire] run function scorched:structure/permafire