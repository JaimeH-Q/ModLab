# Modlab plugin
Hello! Hope you enjoy Modlab :)

## Features
* /staff Mode (Yes, like Staff+) ✅
* Freeze ✅
* BanGUI ⚙️
* XrayGUI⚙️
* Inventory inspect ⚙️
* Reports ⚙️

⚠️ You need at least Paper _not spigot_ 1.18.2 (And so Java 17)

## Use
As I already know you don't want to change your ban plugin,
I instead provide command-based actions (Like command execute when a frozen player leaves):
```yaml
freeze_leave_command: "ban %player% 15d Disconnect while frozen"
```
But I do provide a working reports system.

## Also highly configurable!
You'll be able to toggle almost everything. If you want more customization
just tell me! Most options are self-explanatory but i'll explain some in detail:
````yaml
staff_gamemode: SURVIVAL # SURVIVAL | CREATIVE
# You can choose whether your staff to be on SURVIVAL or CREATIVE
# If you choose SURVIVAL, they will gain fly while they are on duty
# Be careful. as people in survival won't be able to place new blocks
# People who enter staff mode in creative will remain in creative

staff_items_move: false # Can staff items be freely moved inside the inventory?
# You are able to decide if players can place (or not) blocks while on duty.
# This should help to organize your inventory easily. The items can't be dropped


````
