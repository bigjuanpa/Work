/**
 *  SmartUY- RM Bridge TV Remote
 *
 */
 
preferences {    
	section("Internal Access"){
        input "internal_ip", "text", title: "IP for RPI Bridge", description: "(ie. 192.168.0.1)", required: true
        input "internal_port", "text", title: "Port", description: "(ie. 7474)" , required: false
        input "internal_Broadlink_ip", "text", title: "IP of Broadlink", description: "(ie. 192.168.0.2)", required: true
        input "internal_Broadlink_Mac", "text", title: "Mac of Broadlink", description: "(mac with out :)", required: true
        input(name: "DevicePostGet", type: "enum", title: "POST or GET", options: ["POST","GET"], defaultValue: "POST", required: false, displayDuringSetup: true)
		input("HTTPAuth", "bool", title:"Requires User Auth?", description: "Choose if the HTTP requires basic authentication", defaultValue: false, required: true, displayDuringSetup: true)
		input("HTTPUser", "string", title:"HTTP User", description: "Enter your basic username", required: false, displayDuringSetup: true)
		input("HTTPPassword", "string", title:"HTTP Password", description: "Enter your basic password", required: false, displayDuringSetup: true)
		input "POWEROFF", "text", title: "TV On/Off", description: "(RM Bridge code name)" , required: true
        input "SOUND", "text", title: "Sound On/Off", description: "(RM Bridge code name)" , required: false
        input "STB", "text", title: "Set Top Box On/Off", description: "(RM Bridge code name)" , required: false
        input "MUTE", "text", title: "Mute", description: "(RM Bridge code name)" , required: false
        input "HDMI", "text", title: "Source", description: "(RM Bridge code name)" , required: false
        input "MENU", "text", title: "Menu", description: "(RM Bridge code name)" , required: false
        input "TOOLS", "text", title: "Sound Source", description: "(RM Bridge code name)" , required: false
        input "SLEEP", "text", title: "Sleep", description: "(RM Bridge code name)" , required: false
        input "UP", "text", title: "Up", description: "(RM Bridge code name)" , required: false
        input "DOWN", "text", title: "Down", description: "(RM Bridge code name)" , required: false
        input "LEFT", "text", title: "Left", description: "(RM Bridge code name)" , required: false
        input "RIGHT", "text", title: "Right", description: "(RM Bridge code name)" , required: false
        input "CHUP", "text", title: "Channel Up", description: "(RM Bridge code name)" , required: false
        input "CHDOWN", "text", title: "Channel Down", description: "(RM Bridge code name)" , required: false
        input "PRECH", "text", title: "Prev Channel", description: "(RM Bridge code name)" , required: false
        input "EXIT", "text", title: "Exit", description: "(RM Bridge code name)" , required: false
        input "VOLUP", "text", title: "Volume Up", description: "(RM Bridge code name)" , required: false
        input "VOLDOWN", "text", title: "Volume Down", description: "(RM Bridge code name)" , required: false
        input "ENTER", "text", title: "Enter/OK", description: "(RM Bridge code name)" , required: false
        input "RETURN", "text", title: "Return", description: "(RM Bridge code name)" , required: false
        input "INFO", "text", title: "Info", description: "(RM Bridge code name)" , required: false
        input "PICTURE_SIZE", "text", title: "Picture Size", description: "(RM Bridge code name)" , required: false
	}
}
 
metadata {
	definition (name: "SmartUY - TV-Broadlink - RPI", namespace: "SmartUY", author: "SmartUY") {
        capability "switch" 
        capability "Actuator"
		capability "Refresh"
		capability "Sensor"
        capability "Configuration"
        command "mute" 
        command "source"
        command "menu"    
        command "tools"           
        command "HDMI"    
        command "Sleep"
        command "Up"
        command "Down"
        command "Left"
        command "Right" 
        command "chup" 
        command "chdown"               
        command "prech"
        command "volup"    
        command "voldown"           
        command "Enter"
        command "Return"
        command "Exit"
        command "Info"            
        command "Size"
        command "sound"
        command "stb"
        command "all"
	}

    standardTile("switch", "device.switch", canChangeIcon: true) {
        state "on", label:'TV ON', action:"switch.off", icon:"st.Electronics.electronics15", backgroundColor:"#00a0dc"
        state "off", label:'TV OFF', action:"switch.on", icon:"st.Electronics.electronics15", backgroundColor:"#ffffff"
    }
	
        
    
    //standardTile("power", "device.switch", decoration: "flat", canChangeIcon: false) {
    //    state "default", label:'TV', action:"switch.off", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"    
    //}
    
		tiles (scale: 1){      
			multiAttributeTile(name:"power", type: "generic", width: 4, height: 4, canChangeIcon: true){
				tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
					attributeState "on", label:'${name}', action:"switch.off", backgroundColor:"#00a0dc", icon: "st.Electronics.electronics18", nextState:"turningOff"
					attributeState "off", label:'${name}', action:"switch.on", backgroundColor:"#ffffff", icon: "st.Electronics.electronics18", nextState:"turningOn"
					attributeState ("turningOn", label:'${name}', action:"", backgroundColor:"#00a0dc", icon: "st.Electronics.electronics18")
					attributeState ("turningOff", label:'${name}', action:"", backgroundColor:"#ffffff", icon: "st.Electronics.electronics18")
			}
        }           
      }          
    standardTile("sound", "device.switch", decoration: "flat", width: 1, height: 1, canChangeIcon: false) {
        state "default", label:'Sound', action:"sound", icon:"st.Electronics.electronics13", backgroundColor:"#888888"
    }  
    standardTile("stb", "device.switch", decoration: "flat", width: 1, height: 1, canChangeIcon: false) {
        state "default", label:'Proyector', action:"stb", icon:"st.samsung.da.RAC_ic_aircon", backgroundColor:"#888888"
    }  
    standardTile("source", "device.switch", decoration: "flat", width: 1, height: 1, canChangeIcon: false) {
        state "default", label:'Source', action:"source", icon:"st.thermostat.heating-cooling-off"
    }
    standardTile("mute", "device.mute", decoration: "flat", width: 2, height: 2,  canChangeIcon: false) {
        state "default", label:'Mute', action:"mute", icon:"st.custom.sonos.muted" 
    }     
	standardTile("tools", "device.switch", decoration: "flat", width: 2, height: 2,  canChangeIcon: false) {
        state "default", label:'Sound Source', action:"tools", icon:"st.secondary.tools"
    }
	standardTile("menu", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Menu', action:"menu", icon:"st.vents.vent"
    }
	standardTile("HDMI", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Source', action:"HDMI", icon:"st.Electronics.electronics15"
    }
    standardTile("Sleep", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Sleep', action:"Sleep", icon:"st.Bedroom.bedroom10"
    }
    standardTile("Up", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Up', action:"Up", icon:"st.thermostat.thermostat-up"
    }
    standardTile("Down", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Down', action:"Down", icon:"st.thermostat.thermostat-down"
    }
    standardTile("Left", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Left', action:"Left", icon:"st.thermostat.thermostat-left"
    }
    standardTile("Right", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Right', action:"Right", icon:"st.thermostat.thermostat-right"
    }  
	standardTile("chup", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'CH Up', action:"chup", icon:"st.thermostat.thermostat-up"
    }
	standardTile("chdown", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'CH Down', action:"chdown", icon:"st.thermostat.thermostat-down"
    }
	standardTile("prech", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Pre CH', action:"prech", icon:"st.secondary.refresh-icon"
    }
    standardTile("volup", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Vol Up', action:"volup", icon:"st.thermostat.thermostat-up"
    }
    standardTile("voldown", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Vol Down', action:"voldown", icon:"st.thermostat.thermostat-down"
    }
    standardTile("Enter", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Enter', action:"Enter", icon:"st.illuminance.illuminance.dark"
    }
    standardTile("Return", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Return', action:"Return", icon:"st.secondary.refresh-icon"
    }
    standardTile("Exit", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Exit', action:"Exit", icon:"st.locks.lock.unlocked"
    }    
    standardTile("Info", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Info', action:"Info", icon:"st.motion.acceleration.active"
    }    
    standardTile("Size", "device.switch", decoration: "flat",  width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'Picture Size', action:"Size", icon:"st.contact.contact.open"
    }    
    
 
    main "switch"
    details (["power", "chup","prech","volup","chdown","mute","voldown", "HDMI", "Up", "tools", "Left", "Enter", "Right", "Return", "Down", "menu","Exit","Sleep"])	
}

 
def tvAction(String buttonPath) {
	def host = internal_ip
	def hosthex = convertIPtoHex(host).toUpperCase()
	def LocalDevicePort = ''
	if (internal_port==null) { LocalDevicePort = "80" } else { LocalDevicePort = internal_port }
	def porthex = convertPortToHex(LocalDevicePort).toUpperCase()
	device.deviceNetworkId = "$hosthex:$porthex"
	def userpassascii = "${HTTPUser}:${HTTPPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()

	log.debug "The device id configured is: $device.deviceNetworkId"

	def path = "/?IP_Broadlink=" + internal_Broadlink_ip +"&Mac_Broadlink=" + internal_Broadlink_Mac + "&Broadlink_Code=" + buttonPath
	log.debug "path is: $path"
	log.debug "Uses which method: $DevicePostGet"
	def body = "/?IP_Broadlink="+internal_Broadlink_ip+"&Mac_Broadlink="+internal_Broadlink_Mac+"&Broadlink_Code="+buttonPath
	log.debug "body is: $body"

	def headers = [:] 
	headers.put("HOST", "$host:$LocalDevicePort")
	headers.put("Content-Type", "application/x-www-form-urlencoded")
	if (HTTPAuth) {
		headers.put("Authorization", userpass)
	}
	log.debug "The Header is $headers"
	def method = "POST"
	try {
		if (DevicePostGet.toUpperCase() == "GET") {
			method = "GET"
			}
		}
	catch (Exception e) {
		settings.DevicePostGet = "POST"
		log.debug e
		log.debug "You must not have set the preference for the DevicePOSTGET option"
	}
	log.debug "The method is $method"
	try {
		def hubAction = new physicalgraph.device.HubAction(
			method: method,
			path: path,
			body: body,
			headers: headers
			)
		hubAction.options = [outputMsgToS3:false]
		log.debug hubAction
		hubAction
	}
	catch (Exception e) {
		log.debug "Hit Exception $e on $hubAction"
	}
}




def updated() {
 
    if (settings.internal_ip != null && settings.internal_port != null ) {
       device.deviceNetworkId = "${settings.internal_ip}:${settings.port}"
    }
}
def parse(String description) {
	return null
}


def off() {
 finishOff()
	log.debug "Turning TV OFF"
    def powerOFF = POWEROFF 
    tvAction(powerOFF)
}

def finishOff() {
    sendEvent(name: "switch", value: "off", displayed:false, isStateChange: true)
    log.debug "Entre a finishOff"
}

 
def on() {
finishOn()
	log.debug "Turning TV ON"
    def powerOFF = POWEROFF 
    tvAction(powerOFF)
}

def finishOn() {
    sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
    log.debug "Entre a finishOn"
}

//    log.info device.currentValue("level")


// Alexa only sends us 25 for turning up the volume 0 for turning down the volume or it sends a discrete level
// we will treat any value above 25 to increase volume under 25 to decrease volume.  there will some funky behaviour in 
// Alexa App slider but we don't care
def setLevel(val){
    if (val < 0){
    	val = 0
    }
    
    if( val > 100){
    	val = 100
    }
    
    def prevLevel = device.currentValue("level")
    log.info "setLevel $val -- prevLevel $prevLevel"
    
    if ((val > prevLevel) || ((val == prevLevel) && (val != 0))) {
      volup()
      volup()
      sendEvent(name: "switch", value: "on")
    } else {
      voldown()
      voldown()
    }
    
    
    // make sure we don't drive switches past allowed values (command will hang device waiting for it to
    // execute. Never commes back)
    sendEvent(name:"level",value:val)
    sendEvent(name:"switch.setLevel",value:val)
}

def all() {
	log.trace "all pressed"
    off()
    sound()
    stb()
}

def mute() {
	log.trace "MUTE pressed"
    def Mute = MUTE
    tvAction(Mute)    
}

def source() {
	log.debug "SOURCE pressed"
    def Source = SOURCE
    tvAction(Source)     
}

def menu() {
	log.debug "MENU pressed"
    def Menu = MENU
    tvAction(Menu) 
}

def tools() {
	log.debug "TOOLS pressed"
    def Tools = TOOLS
    tvAction(Tools)     
}

def HDMI() {
	log.debug "HDMI pressed"
    def Hdmi = HDMI
    tvAction(Hdmi)     
}

def Sleep() {
	log.debug "SLEEP pressed"
    def Sleep = SLEEP
    tvAction(Sleep)   
}

def sound() {
	log.debug "Sound pressed"
    def Sound = SOUND
    tvAction(Sound) 
     
}
def stb() {
	log.debug "STB pressed"
    def Stb = STB
    tvAction(Stb) 
     
}
def Up() {
	log.debug "UP pressed"
    def Up = UP
    tvAction(Up)
}

def Down() {
	log.debug "DOWN pressed"
    def Down = DOWN
    tvAction(Down) 
}

def Left() {
	log.debug "LEFT pressed"
    def Left = LEFT
    tvAction(Left) 
}

def Right() {
	log.debug "RIGHT pressed"
    def Right = RIGHT
    tvAction(RIGHT) 
}

def chup() {
	log.debug "CHUP pressed"
    def Chup = CHUP 
    tvAction(Chup)           
}

def chdown() {
	log.debug "CHDOWN pressed"
    def Chdown = CHDOWN
    tvAction(Chdown)         
}

def prech() {
	log.debug "PRECH pressed"
    def Prech = PRECH
    tvAction(Prech)
}

def Exit() {
	log.debug "EXIT pressed"
    def Exit = EXIT
    tvAction(Exit) 
}

def volup() {
	log.debug "VOLUP pressed"
    def Volup = VOLUP
    tvAction(Volup)         
}

def voldown() {
	log.debug "VOLDOWN pressed"
    def VoldDown = VOLDOWN
    tvAction(VoldDown)        
}

def Enter() {
	log.debug "ENTER pressed"
    def Enter = ENTER
    tvAction(Enter) 
}

def Return() {
	log.debug "RETURN pressed"
    def Return = RETURN
    tvAction(Return) 
}

def Info() {
	log.debug "INFO pressed"
    def Info = INFO
    tvAction(Info)   
}

def Size() {
	log.debug "PICTURE_SIZE pressed"
    def PicSize = PICTURE_SIZE
    tvAction(PicSize) 
}

def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
	def nameAndValue = param.split(":")
	map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}
private String convertIPtoHex(ipAddress) { 
	String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
	//log.debug "IP address entered is $ipAddress and the converted hex code is $hex"
	return hex
}
private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
	//log.debug hexport
	return hexport
}
private Integer convertHexToInt(hex) {
	Integer.parseInt(hex,16)
}
private String convertHexToIP(hex) {
	//log.debug("Convert hex to ip: $hex") 
	[convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}
private getHostAddress() {
	def parts = device.deviceNetworkId.split(":")
	//log.debug device.deviceNetworkId
	def ip = convertHexToIP(parts[0])
	def port = convertHexToInt(parts[1])
	return ip + ":" + port
}