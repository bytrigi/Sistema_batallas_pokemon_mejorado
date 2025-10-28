## 🧩 Mejora del Sistema de Batallas Pokémon

Mejora del **Sistema de Batallas Pokémon** implementado anteriormente.  
Ahora se **añaden ciertas funciones**. En concreto, las siguientes:

---

### ⚙️ 1. Imprimir el estado de un Pokémon
- La función **recibe como parámetros** los valores actuales del Pokémon (vida, ataque, defensa, etc.).  
- Muestra su estado actual en pantalla.

---

### 🗡️ 2. Pedir al usuario qué ataque realiza
- La función **pregunta al usuario** qué ataque quiere usar.  
- Si el usuario introduce un valor incorrecto, **vuelve a solicitar la información**.  
- Ejemplo:
  > “Pulsa 1 para el ataque principal, 2 para el especial.”

---

### 💥 3. Realizar un ataque
- La función recibe como parámetros:
  - El **daño de ataque**
  - La **vida del enemigo**
  - Su **defensa**
- Devuelve **cuánta vida le queda al enemigo después del ataque**.

---

✨ *El objetivo es modularizar el sistema y mejorar la legibilidad del código mediante funciones bien definidas.*
