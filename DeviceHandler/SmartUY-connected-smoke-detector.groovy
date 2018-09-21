/**
 *  SmartUY Cloud Connected Smoke Detector
 *
 */

metadata {
  definition (name: "SmartUY Connected Smoke Detector", namespace: "SmartUY", author: "SmartUY") {
    capability "Smoke Detector"
    command "open"
    command "close"
  }

  tiles {
    standardTile("smoke", "device.smoke", width: 2, height: 2) {
      state "detected", label: '${name}', icon: "st.alarm.smoke.smoke"
      state "clear", label: '${name}', icon: "st.alarm.smoke.clear"
    }

    main "smoke"
    details "smoke"
  }
}

def open() {
  sendEvent(name: "smoke", value: "detected")
}

def close() {
  sendEvent(name: "smoke", value: "clear")
}