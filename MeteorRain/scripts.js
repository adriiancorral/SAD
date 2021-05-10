/*
    Clases
        Controller: Se encarga del control general del juego
        Player: Se encarga de controlar al jugador (movimientos, vida, animaciones, ...)
        View: Se encarga de controlar gráficamente el juego
        Meteors: Se encarga de lanzar los meteoritos
        HitDetector: Se encarga de detectar si una explosión de meteorito ha golpeado al jugador
        Timer: Se encarga de controlar el cronometro del juego
        Score: Se encarga de gestionar el score
*/

class Controller {
    constructor() {
        this.player = new Player(); // Creamos el player
        this.view = new View(); // Creamos la view
        this.meteors = new Meteors(); // Creamos el lanzador de meteoritos
        this.hits = new HitDetector(); // Creamos el detector de golpes
        this.timer = new Timer(); // Creamos el timer
        this.score = new Score(); // Creamos el score


        // Guardamo los elementos de la pantalla de Start Game
        this.startScreen = document.getElementById("start_screen");
        this.scoreHTML = document.getElementById("score");
        this.startButton = document.getElementById("start_button");

        // Ponemos el valor del mejor score en la pantalla de Start Game
        this.scoreHTML.innerHTML = `Best score: ${localStorage.bestScore}`;

        this.wKey = false;
        this.sKey = false;
        this.aKey = false;
        this.dKey = false;

        this.frameRate = 60;

        // Escuchamos el evento de actual_time para ir modificando la velocidad
        // de caida de los meteoritos
        document.addEventListener("actual_time", (event) => {
            this.meteorsFrecuencyController(event.detail.time);
        })

        // Escuchamos el evento de player_dead para saber si ha muerto el jugador
        // Si muere ejecutamos la funcion de gameover
        document.addEventListener("player_dead", () => {
            this.stop();
        });

        // Escuchamos el evento de click del boton de Start Game. Cuando le damos
        // click se inicia el juego ejecutando la funcion start()
        this.startButton.addEventListener("click", () => {
            this.startScreen.style.visibility = "hidden";
            this.start();
        })

        // Escuchamos el evento de gameover para volver a hacer visible la ventana
        // de Start Game y actualizar la mejor puntuación
        document.addEventListener("gameover", () => {
            // Esperamos 1 segundo para que la ventana no salga instantaneamente
            // despues de perder la partida
            setTimeout(() => {
                this.scoreHTML.innerHTML = `Best score: ${localStorage.bestScore}`;
                this.startScreen.style.visibility = "visible";
            }, 1000);
        })
    }

    start() {
        this.startKeyReader();
        this.frameInterval = setInterval(() => {
            this.frame();
        }, 1000 / this.frameRate);

        this.timer.start();
        this.player.start();
        this.meteors.start(1000); // Lanzamos un meteorito cada segundo
    }

    stop() {
        setTimeout( () => {
            clearInterval(this.frameInterval);
        }, 100);

        // Cuando perdemos la partida lanzamos el evento gameover
        let event = new CustomEvent("gameover");
        document.dispatchEvent(event);

        // Paramos de leer las teclas
        this.stopKeyReader();
    }

    makeStartGameWindow() {

    }

    // Esta función se encarga de leer las teclas pulsadas
    startKeyReader() {
        this.keydownFunction = (event) => {
            switch (event.key) {
                case "w":
                    this.wKey = true;
                    break;
                case "s":
                    this.sKey = true;
                    break;
                case "a":
                    this.aKey = true;
                    break;
                case "d":
                    this.dKey = true;
                    break;
            }
        }
        this.keyupFunction = (event) => {
            switch (event.key) {
                case "w":
                    this.wKey = false;
                    break;
                case "s":
                    this.sKey = false;
                    break;
                case "a":
                    this.aKey = false;
                    break;
                case "d":
                    this.dKey = false;
                    break;
            }
        }

        document.addEventListener("keydown", this.keydownFunction);
        document.addEventListener("keyup", this.keyupFunction);
    }

    // Esta función se encarga de parar de leer las teclas pulsadas
    stopKeyReader() {
        // Quitamos los EventListener
        document.removeEventListener("keydown", this.keydownFunction);
        document.removeEventListener("keyup", this.keyupFunction);

        // Ponemos todas las teclas en false por si alguna se ha quedado pulsada
        this.wKey = false;
        this.sKey = false;
        this.aKey = false;
        this.dKey = false;
    }

    frame(){
        this.player.animatePlayer(this.wKey, this.sKey, this.aKey, this.dKey);
    }

    meteorsFrecuencyController(time){
        var frecuency = 2*time/10;
        var period_milisecons = 1000/frecuency;

        this.meteors.stopMeteorRain();
        this.meteors.meteorRain(period_milisecons);
    }

}

//-------------------------------------------------------------------

class Player {
    constructor(){
        this.type = "Player";
        this.health = 1;

        this.playerHTML = document.getElementById("player");
        this.playerTop = this.playerHTML.offsetTop;
        this.playerLeft = this.playerHTML.offsetLeft;
        this.playerHeight = this.playerHTML.offsetHeight;
        this.playerWidth = this.playerHTML.offsetWidth;
        this.walkingSpeed = 5;
        this.playerState = "idle";
        this.playerOrientation = "right";
        this.playerHurt = false;


        this.screenHTML = document.getElementById("screen");
        this.screenHeight = this.screenHTML.offsetHeight;
        this.screenWidth = this.screenHTML.offsetHeight;

        // Definimos el sonido que sonara cuando el jugador sea golpeado
        this.damageSound = new Audio("assets/hurt_sound.mp3");

        // Creamos un EventListener que escuche si le han hecho daño al player
        document.addEventListener("damage", (event) => {
            this.dealDamage(event);
        });
    }

    start() {
        this.health = 1;
    }

    animatePlayer(upKey, downKey, leftKey, rightKey) {
        this.movePlayer(upKey, downKey, leftKey, rightKey);
        this.changePlayerState(upKey, downKey, leftKey, rightKey);
        this.changePlayerOrientation(leftKey, rightKey);
        
        this.notifyView();
    }

    movePlayer(upKey, downKey, leftKey, rightKey) {
        if (upKey) {
            this.playerTop = this.playerTop - this.walkingSpeed;
            // Si nos pasamos de la pantalla vuelve a 0
            if (this.playerTop < 0){
                this.playerTop = 0;
            }
        }
        if (downKey) {
            this.playerTop = this.playerTop + this.walkingSpeed;
            // Si nos pasamos de la pantalla vuelve a abajo
            if (this.playerTop + this.playerHeight > this.screenHeight){
                this.playerTop = this.screenHeight - this.playerHeight;
            }
        }
        if(leftKey) {
            this.playerLeft = this.playerLeft - this.walkingSpeed;
            if(this.playerLeft < 0){
                this.playerLeft = 0;
            }
        }
        if(rightKey) {
            this.playerLeft = this.playerLeft + this.walkingSpeed;
            if(this.playerLeft + this.playerWidth > this.screenWidth){
                this.playerLeft = this.screenWidth - this.playerWidth;
            }
        }
    }

    changePlayerState(upKey, downKey, leftKey, rightKey) {
        
        // Si el jugador esta dañado primero tenemos la animación del daños
        // Despues, si no estamos moviendonos para nigún lado el jugador esta quieto
        // Si nos estamos moviendo el jugador esta andando
        if(this.playerHurt){
            this.playerState = "hurt";
        }else if(upKey == downKey && leftKey == rightKey) {
            this.playerState = "idle";
        } else {
            this.playerState = "walking";
        }
    }

    changePlayerOrientation(leftKey, rightKey) {
        if(leftKey && !rightKey) {
            this.playerOrientation = "left";
        } else if (rightKey && !leftKey) {
            this.playerOrientation = "right";
        }
    }

    dealDamage(event) {
        // Decimos que el jugador esta dañado y a los 100 milisegundos
        // le quitamos el estado de dañado
        this.playerHurt = true;
        setTimeout(() => {
            this.playerHurt = false;
        }, 500);

        // Cuando nos hacen daño obtenemos la cantidad de daño del evento y
        // le restamos ese daño a la vida
        let damage = event.detail.damage;
        this.health = this.health - damage;

        // Definimos el sonido que sonara cuando el jugador sea golpeado y lo activamos
        var damageSound = new Audio("assets/hurt_sound.mp3");
        damageSound.play();

        // Si la vida es menor a 0, enviamos un evento de player_dead para avisar de que el jugador
        // esta muerto
        if(this.health <= 0){
            let event = new CustomEvent("player_dead");
            document.dispatchEvent(event);
        }
    }

    notifyView() {
        var event = new CustomEvent("updateView", {
            detail: this
        });
        document.dispatchEvent(event);
    }
}

//-------------------------------------------------------------------

class View {
    constructor() {

        // Escuchamos las llamadas y ejecutamos update con la 
        // información del evento updateView
        document.addEventListener("updateView", (event) => {
            this.update(event);
        });
    }

    update(event) {
        // Cogemos la información del evento
        var info = event.detail;

        // Dependiendo del del objeto que envie el evento actualizamos unas 
        // propiedades u otras
        switch(info.type) {
            case "Player":
                this.updatePlayer(info);
        }
    }

    updatePlayer(info) {
        let playerHTML = document.getElementById("player");

        // Actualizamos la posición
        playerHTML.style.top = info.playerTop + "px";
        playerHTML.style.left = info.playerLeft + "px";

        // Actualizamos la animación mirando el estado
        let actualGIF = playerHTML.src.substring(playerHTML.src.lastIndexOf('/') + 1);
        let newGIF = `dino_${info.playerState}.gif`;

        // Si el GIF actual es distinto al nuevo, lo cambiamos. Sino no hacemos nada.
        if(actualGIF != newGIF){
            playerHTML.src = "assets/" + newGIF;
        }

        // Actualizamos la orientación del GIF
        switch(info.playerOrientation){
            case "right":
                playerHTML.style.transform = "scaleX(1)";
                break;
            case "left":
                playerHTML.style.transform = "scaleX(-1)";
                break;
        }
    }
}

//-------------------------------------------------------------------

class Meteors {
    constructor() {
        this.screenHTML = document.getElementById("screen");

        this.id = 0;

        // Si se lanza el evento gameover ha acabado la partida así que paramos
        // de lanzar meteoritos
        document.addEventListener("gameover", () => {
            this.stopMeteorRain();
        });
    }

    start() {
        this.id = 0;
        this.meteorRain(1000);
    }

    meteorRain(intervalTime) {
        this.rainInterval = setInterval( () => {
            this.throwMeteor(this.id);
            this.id = this.id + 1;
        }, intervalTime, this.id)
    }

    stopMeteorRain() {
        clearInterval(this.rainInterval);
    }

    throwMeteor(id) {
        // Calculamos una posición aleatoria
        let xPos = Math.random() * this.screenHTML.offsetWidth;
        let yPos = Math.random() * this.screenHTML.offsetHeight;

        // Creamos el meteorito y le ponemos el id y la clase
        var newMeteor = document.createElement("img");
        newMeteor.id = `meteor${id}`;
        newMeteor.classList.add("meteor_shadow");

        // Añadimos la imagen de la sombra
        newMeteor.src = "assets/meteor_shadow_center.png";

        //Añadimos la posición del meteorito
        newMeteor.style.marginLeft = `${xPos}px`;
        newMeteor.style.marginTop = `${yPos}px`;

        // Añadimos el meteor a la pantalla
        this.screenHTML.appendChild(newMeteor);

        // Cuando pase 1 segundo tendremos la explosión del meteorito y calculamos
        // si el usuario estaba en la explosion
        setTimeout( () => {
            this.meteorExplosion(id);
        }, 2000, id); // Pasamos la id como parametro;

        // Al rato borramos el meteorito para que no queden restros en el html
        setTimeout( () => {
            document.getElementById(`meteor${id}`).remove();
        }, 4000, id);
    }

    meteorExplosion(id) {
        let meteor = document.getElementById(`meteor${id}`);

        // Encontramos los lados de la explosión para el choque
        let explosionLeftX = meteor.offsetLeft - meteor.offsetWidth/4;
        let explosionRightX = meteor.offsetLeft + meteor.offsetWidth/4;
        let explosionTopY = meteor.offsetTop - meteor.offsetWidth/8;
        let explosionBottomY = meteor.offsetTop + meteor.offsetWidth/8;

        // Cambiamos la clase del meteorito para que deje de ser la sombra y se active
        // la animación de la explosión
        meteor.classList.remove("meteor_shadow");
        meteor.classList.add("meteor_explosion");

        // Activamos el sonido de la explosion del meteorito
        var explosionSound = new Audio("assets/explosion_sound.mp3");
        explosionSound.volume = 0.2; //Le bajamos el sonido
        explosionSound.play();
        
        // Cuando cae el meteorito activamos el evento de explosion, en el evento vamos a enviar
        // los datos de la hitbox del meteorito
        let event = new CustomEvent("explosion", {
            detail: {
                explosionLeftX: explosionLeftX,
                explosionRightX: explosionRightX,
                explosionTopY: explosionTopY,
                explosionBottomY: explosionBottomY
            }
        });
        document.dispatchEvent(event);
    }
}

//-------------------------------------------------------------------

class HitDetector {
    constructor() {
        // Creamos un EventListener que escuche las explosiones de los meteoritos
        document.addEventListener("explosion", (event) => {
            this.detectExplosionHit(event);
        });
    }

    detectExplosionHit(event) {
        let player = document.getElementById("player");

        // Encontramos los valores centrales del player
        let playerCenterX = player.offsetLeft + player.offsetWidth/2;
        let playerCenterY = player.offsetTop + player.offsetWidth/2;

        // Guardamos los valores de la hitbox de la explosion
        let explosionLeftX = event.detail.explosionLeftX;
        let explosionRightX = event.detail.explosionRightX;
        let explosionTopY = event.detail.explosionTopY;
        let explosionBottomY = event.detail.explosionBottomY;

        // Detectamos si la explosión se ha dado, si es así le hacemos daño al player
        if(explosionLeftX < playerCenterX && explosionRightX > playerCenterX && explosionTopY < playerCenterY && explosionBottomY > playerCenterY){

            // Cuando le hacemos daño al player enviamos un evento damage con la cantidad de daño
            // En este caso vamos a hacer 1 de daño
            let event = new CustomEvent("damage", {
                detail: {
                    damage: 1
                }
            });
            document.dispatchEvent(event);
        }
    }
}

//-------------------------------------------------------------------
class Timer {
    constructor() {
        this.timer = document.getElementById("timer");

        this.time = 0;

        // Escuchamos el evento gameover, cuando se ejecuta la partida a acabado
        // así que paramos el timer
        document.addEventListener("gameover", () => {
            this.stop();
        });
    }

    start() {
        this.time = 0;
        this.timerInterval = setInterval( () => {
            document.getElementById("timer").innerHTML = `${this.time}`;
            this.time = this.time + 1;

            // Por cada 10 ticks que pasa lanzamos un evento con el tiempo
            // para poder hacer modificaciones del juego segun el tiempo
            if(this.time % 10 == 0) {
                let event = new CustomEvent("actual_time", {
                    detail: {
                        time: this.time
                    }
                });
                document.dispatchEvent(event);
            }    

        }, 1000);
    }

    stop() {
        // Paramos el timer
        clearInterval(this.timerInterval);

        // Enviamos un evento con el score que es igual al tiempo que ha aguantado
        // el jugador
        let event = new CustomEvent("score", {
            detail: {
                score: this.time-1
            }
        });
        document.dispatchEvent(event);
    } 
}

//-------------------------------------------------------------------

class Score {
    constructor() {
        // Score se encarga de escuchar los eventos de score para guardar la
        // puntuación del jugador.
        document.addEventListener("score", (event) => {
            this.saveBestScore(event.detail.score);
        });
    }

    saveBestScore(newScore) {
        // Si no hay ninguna score guardada guardamos la nueva como la mejor y si
        // hay alguna guardada comparamos la nueva con la guardada y guardamos la
        // que sea mas grande
        if(typeof localStorage.bestScore != "string"){
            localStorage.bestScore = newScore;
        } else if (localStorage.bestScore < newScore) {
            localStorage.bestScore = newScore;
        }
    }
}

//-------------------------------------------------------------------

let controller = new Controller();













