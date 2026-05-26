# Create: Testosterone (Fabric Port)

A Fabric port of **[Create: Testosterone](https://github.com/mifort-github/create_testosterone)**, the Forge/NeoForge addon for the **Create Mod** that introduces the process line for Testosterone Pills and the *Man Power* effect.

> [!NOTE]  
> This project is a Fabric port of the original Forge/NeoForge addon created by **mifort**. All original assets, textures, and design concepts belong to the original author.

---

## 🧪 Features

With the addition of the **Testosterone Pill**, a new processing line and the **Man Power** effect are introduced:
- **Man Power Effect**: Gives you the ability to negate damage for a few hits before triggering a cooldown. Extremely useful in combat!
- **Custom Player Visuals**: Custom features/models on players when under the influence of *Man Power*.
- **Rat Entities**: Custom rat mobs.
- **Steroids & Additions**: Trenbolone, cholesterol, beer, and zinc processing.
- **Ties**: Formal business ties for professional Minecraft meetings.

---

## 🛠️ The Porting Pipeline

This mod was successfully ported from **Forge** to **Fabric (Minecraft 1.20.1)**. Below is the technical pipeline utilized to complete the transition:

### 1. Development Environment & SDK Migration
- Set up a clean Fabric Loader environment with Minecraft 1.20.1 using the Fabric Loom Gradle plugin.
- Ported the Gradle configuration (`build.gradle`, `settings.gradle`, `gradle.properties`) to reference Fabric-compatible mappings, Loom, and dependencies.
- Re-routed dependencies to Fabric equivalents (e.g., Fabric API, Create Fabric API, and Catnip).

### 2. Registry & Lifecycle Migration
- Replaced Forge's deferred registry system (`DeferredRegister`) with Fabric's registry patterns (`Registry.register()`).
- Migrated the main mod entry point from a Forge mod class using `@Mod` to a standard Fabric entry point implementing `ModInitializer`.
- Migrated event handlers and listener registrations (e.g., entity attributes, loot modifiers) to Fabric API events (such as `FabricDefaultAttributeRegistry`).

### 3. Namespace & Package Refactoring
- Refactored the internal packages from `net.mifort.testosterone` to `net.mervyn.testosterone` to reflect the new port ownership and maintain clean namespaces.
- Updated all references in JSON resources, data tags, and mixin configurations to use the correct Fabric paths.

### 4. Client-Side Rendering & Network Porting
- Translated Forge client setup events (like model registration, partial models, render layers) to Fabric-native client initializers (`ClientModInitializer`).
- Migrated Forge packet networking pipelines (`SimpleChannel`) to Fabric's networking API.

### 5. IDE Configuration & Clean Build
- Configured workspace settings in `.vscode/settings.json` to disable strict null analysis warnings (`"java.compile.nullAnalysis.mode": "disabled"`), which were generating excessive noise due to mismatching nullability annotations across imported Minecraft/Fabric API boundaries.

---

## 📜 License

This port is distributed under the **GNU General Public License (GPL) Version 3**, matching the original license of **Create: Testosterone** by mifort. See the [LICENSE](file:///d:/projects/createTestosterone-fabric-1.20.1/LICENSE) file for details.

> [!WARNING]  
> **Disclaimer**: We do not promote or condone the use of anabolic steroids as performance enhancers. This mod is for entertainment and gameplay purposes only.
