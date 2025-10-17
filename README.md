# VulkanBobby

A small **compatibility layer** between **[VulkanMod](https://github.com/xCollateral/VulkanMod)** and **[Bobby](https://github.com/Johni0702/bobby/)** (Fabric, Minecraft 1.21.5).  
Keeps Bobby’s cached (fake) chunks visible with VulkanMod.

## Requirements
- Minecraft **1.21.5**, Java **21**
- Fabric Loader **0.17.x**, Fabric API **0.128.x**
- Bobby **5.2.7**, VulkanMod **0.5.6**

## Install
1. Put `vulkanbobby-<version>.jar` into `mods/` together with Bobby and VulkanMod.
2. Launch with Fabric.

## Build
```bash
./gradlew build
# jar: build/libs/vulkanbobby-<version>.jar
```
