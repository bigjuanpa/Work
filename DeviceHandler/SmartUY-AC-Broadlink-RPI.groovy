/**
 *  SmartUY- RM Bridge AC Remote
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
		input "POWEROFF", "text", title: "AC Off", description: "(RM Bridge code name)" , required: true
        input "POWERON", "text", title: "AC On", description: "(RM Bridge code name)" , required: true
        input "FRIO16", "text", title: "Frio 16 Grados", description: "(RM Bridge code name)" , required: false
        input "FRIO18", "text", title: "Frio 18 Grados", description: "(RM Bridge code name)" , required: false
        input "FRIO20", "text", title: "Frio 20 rados", description: "(RM Bridge code name)" , required: false
        input "CALOR20", "text", title: "Calor 20 Grados", description: "(RM Bridge code name)" , required: false
        input "CALOR24", "text", title: "Calor 24 Grados", description: "(RM Bridge code name)" , required: false
        input "CALOR30", "text", title: "Calor 30 Grados", description: "(RM Bridge code name)" , required: false
	}
}
 
metadata {
	definition (name: "SmartUY - AC-Broadlink - RPI", namespace: "SmartUY", author: "SmartUY") {
        capability "switch" 
        capability "Actuator"
		capability "Refresh"
		capability "Sensor"
        capability "Configuration"
		command "poweron"
        command "poweroff" 
        command "frio16"
        command "frio18"
        command "frio20"    
        command "calor20"           
        command "calor24" 
        command "calor30"
	}

    standardTile("switch", "device.switch", canChangeIcon: true) {
        state "on", label:'AC ON', action:"switch.off", icon:"st.Home.home1", backgroundColor:"#00a0dc"
        state "off", label:'AC OFF', action:"switch.on", icon:"st.Home.home1", backgroundColor:"#ffffff"
    }

  //  standardTile("poweroff", "device.switch", width: 3, height: 1, decoration: "flat", canChangeIcon: false) {
  //      state "default", label:'AC Off', action:"switch.off", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
  //  }  
  //  standardTile("poweron", "device.switch", width: 3, height: 1, decoration: "flat", canChangeIcon: false) {
  //      state "default", label:'AC ON', action:"switch.on", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
  //  }
  
  
  			tiles (scale: 1){      
			multiAttributeTile(name:"power", type: "generic", width: 4, height: 4, canChangeIcon: true){
				tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
					attributeState "on", label:'${name}', action:"switch.off", backgroundColor:"#00a0dc", icon: "st.Home.home1", nextState:"turningOff"
					attributeState "off", label:'${name}', action:"switch.on", backgroundColor:"#ffffff", icon: "st.Home.home1", nextState:"turningOn"
					attributeState ("turningOn", label:'${name}', action:"", backgroundColor:"#00a0dc", icon: "st.Home.home1")
					attributeState ("turningOff", label:'${name}', action:"", backgroundColor:"#ffffff", icon: "st.Home.home1")
			}
        }           
      } 
  
  
    standardTile("frio16", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'16°', action:"frio16", icon:"st.Weather.weather7", backgroundColor:"#66B2FF"
    }  
    standardTile("frio18", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'18°', action:"frio18",icon:"st.Weather.weather7", backgroundColor:"#66B2FF"
    }
    standardTile("frio20", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'20°', action:"frio20", icon:"st.Weather.weather7", backgroundColor:"#66B2FF"
    }  
    standardTile("calor20", "device.switch", decoration: "flat", width: 2, height: 2, canChangeIcon: false) {
        state "default", label:'20°', action:"calor20", icon:"st.Weather.weather14", backgroundColor:"#FF7514"
    }
    standardTile("calor24", "device.switch", decoration: "flat", width: 2, height: 2,  canChangeIcon: false) {
        state "default", label:'24°', action:"calor24", icon:"st.Weather.weather14", backgroundColor:"#FF7514" 
    }  
    standardTile("calor30", "device.switch", decoration: "flat", width: 2, height: 2,  canChangeIcon: false) {
        state "default", label:'30°', action:"calor30", icon:"st.Weather.weather14", backgroundColor:"#FF7514"
    } 
    main "switch"
    details (["power","frio16",,"frio18","frio20", "calor20","calor24","calor30"])	
}

 
def AcAction(String buttonPath) {
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
    def powerOFF = POWEROFF 
    AcAction(powerOFF)
    //sendEvent(name: "switch", value: "off", displayed:false, isStateChange: true)
}

def finishOff() {
    sendEvent(name: "switch", value: "off", displayed:false, isStateChange: true)
    log.debug "Entre a finishOff"
}
 
def on() {
    finishOn()
    def powerON = POWERON 
    AcAction(powerON)
    //sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
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

def frio16() {
    finishOn()
	log.trace "FRIO16 pressed"
    def Frio16 = FRIO16
    AcAction(Frio16)
    sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
}

def frio18() {
    finishOn()
	log.trace "FRIO18 pressed"
    def Frio18 = FRIO18
    AcAction(Frio18)
    sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
}

def frio20() {
    finishOn()
	log.debug "FRIO20 pressed"
    def Frio20 = FRIO20
    AcAction(Frio20)  
    sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
}

def calor20() {
    finishOn()
	log.debug "CALOR20 pressed"
    def Calor20 = CALOR20
    AcAction(Calor20) 
    sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
}

def calor24() {
    finishOn()
	log.debug "CALOR24 pressed"
    def Calor24 = CALOR24
    AcAction(Calor24)  
    sendEvent(name: "switch", value: "on", displayed:false, isStateChange: true)
}

def calor30() {
    finishOn()
    def Calor30 = CALOR30
    AcAction(Calor30)
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