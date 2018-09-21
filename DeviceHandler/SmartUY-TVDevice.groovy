/**
 *  TVDevice v1.0.20180502
 *
 *
 *
 */

import groovy.json.JsonSlurper

metadata {
	definition (name: "SmartUY-TVDevice", author: "SmartUY", namespace:"SmartUY") {
		capability "Switch"
		capability "Switch Level"
		attribute "displayName", "string"
		command "tvinput"
		command "chanup"
		command "chandown"
		command "tvprev"
		command "volup"
		command "voldown"
		command "tvmute"
		command "ResetTiles"
	}

	preferences {
		input("DeviceIP", "string", title:"Device IP Address", description: "Please enter your device's IP Address", required: true, displayDuringSetup: true)
		input("DevicePort", "string", title:"Device Port", description: "Empty assumes port 80.", required: false, displayDuringSetup: true)
		input(name: "DevicePath", type: "enum", title:"Path del Modelo de TV", options: ["/ir?tvSAMSUNG","/ir?tvPANAVOX","/ir?tvHPC"], defaultValue: "/ir?tvSAMSUNG", required: true, displayDuringSetup: true)
		//input("DevicePathOff", "string", title:"URL Path for ON", description: "Rest of the URL, include forward slash.", displayDuringSetup: true)
		input(name: "DevicePostGet", type: "enum", title: "POST or GET", options: ["POST","GET"], defaultValue: "POST", required: false, displayDuringSetup: true)
		section() {
			input("HTTPAuth", "bool", title:"Requires User Auth?", description: "Choose if the HTTP requires basic authentication", defaultValue: false, required: true, displayDuringSetup: true)
			input("HTTPUser", "string", title:"HTTP User", description: "Enter your basic username", required: false, displayDuringSetup: true)
			input("HTTPPassword", "string", title:"HTTP Password", description: "Enter your basic password", required: false, displayDuringSetup: true)
		}
	}
	
	simulator {
	}

	tiles(scale: 2) {
		valueTile("displayName", "device.displayName", width: 6, height: 1, decoration: "flat") {
			state("default", label: '${currentValue}', backgroundColor:"#DDDDDD")
		}
		//valueTile("displayName", "device.displayName", width: 6, height: 1, decoration: "flat") {
		//	state("default", label: '{currentValue}', backgroundColor:"#ffffff")
		//}
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "off", label:'OFF' , action: "on", icon: "st.Electronics.electronics18", backgroundColor:"#53a7c0", nextState: "trying"
			state "on", label: 'ON', action: "off", icon: "st.Electronics.electronics18", backgroundColor: "#FF6600", nextState: "trying"
			//state "trying", label: 'TRYING', icon: "st.Electronics.electronics18", backgroundColor: "#FFAA33"
		}
		standardTile("switchon", "device.switch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'ON', action: "on", icon: "st.Electronics.electronics18"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.Electronics.electronics18", backgroundColor: "#FFAA33"
		}
		standardTile("switchoff", "device.switch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'OFF' , action: "off", icon: "st.Electronics.electronics18"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.Electronics.electronics18", backgroundColor: "#FFAA33"
		}
		standardTile("tvinput", "device.tvinput", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'TV INPUT', action: "tvinput", icon: "st.Electronics.electronics6"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.Electronics.electronics6", backgroundColor: "#FFAA33"
		}
		standardTile("chanup", "device.chanup", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'CHAN UP', action: "chanup", icon: "st.custom.buttons.add-icon"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.buttons.add-icon", backgroundColor: "#FFAA33"
		}
		standardTile("chandown", "device.chandown", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'CHAN DOWN' , action: "chandown", icon: "st.custom.buttons.subtract-icon"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.buttons.subtract-icon", backgroundColor: "#FFAA33"
		}
		standardTile("tvprev", "device.tvprev", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'PREVIOUS', action: "tvprev", icon: "st.motion.motion.active"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.motion.motion.active", backgroundColor: "#FFAA33"
		}
		standardTile("volup", "device.volup", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'VOL UP', action: "volup", icon: "st.custom.buttons.add-icon"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.buttons.add-icon", backgroundColor: "#FFAA33"
		}
		standardTile("voldown", "device.voldown", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'VOL DOWN' , action: "voldown", icon: "st.custom.buttons.subtract-icon", nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.buttons.subtract-icon", backgroundColor: "#FFAA33"
		}
		standardTile("tvmute", "device.tvmute", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'MUTE', action: "tvmute", icon: "st.custom.sonos.muted"	//, nextState: "trying"
			//state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.sonos.muted", backgroundColor: "#FFAA33"
		}
		controlTile("levelSliderControl", "device.level", "slider", width: 6, height: 1, inactiveLabel: false, range:"(0..100)") {
			//state "level", label:'HDMI Input', action:"switch level.setLevel"
		}
		
		main "switch"
		details(["displayName","levelSliderControl", "switchon", "switchoff", "tvinput", "chanup", "tvprev", "volup", "chandown", "tvmute", "voldown" ])
	}
}

def ResetTiles() {
	sendEvent(name: "switchon", value: "default", isStateChange: true)
	sendEvent(name: "switchoff", value: "default", isStateChange: true)
	sendEvent(name: "tvinput", value: "default", isStateChange: true)
	sendEvent(name: "chanup", value: "default", isStateChange: true)
	sendEvent(name: "chandown", value: "default", isStateChange: true)
	sendEvent(name: "tvprev", value: "default", isStateChange: true)
	sendEvent(name: "volup", value: "default", isStateChange: true)
	sendEvent(name: "voldown", value: "default", isStateChange: true)
	sendEvent(name: "tvmute", value: "default", isStateChange: true)
	log.debug "Resetting tiles."
}


def setLevel(value) {
	def level=value as int
	def cmd="" as String
	log.debug "setLevel >> value: $value"
    if (value<10) {
        level=0
        cmd="=mute"
    } else if (value>=10 && value<=14) {
        level=10
        cmd="/ir?tv=input"
    } else if (value>=15 && value<=24) {
    	level=20
        cmd="/ir?hdmi=" + 1
    } else if (value>=25 && value<=34) {
    	level=30
        cmd="/ir?hdmi=" + 2
    } else if (value>=35 && value<=44) {
    	level=40
        cmd="/ir?hdmi=" + 3
    } else if (value>=45 && value<=54) {
    	level=50
        cmd="/ir?hdmi=" + 4
    } else if (value>=55 && value<=64) {
    	level=60
        cmd="/ir?hdmi=" + 5
    } else if (value>=65 && value<=74) {
    	level=70
        cmd="/ir?hdmi=" + 6
    } else if (value>=75 && value<=84) {
    	level=80
        cmd="/ir?hdmi=" + 7
    } else if (value>=85 && value<=94) {
    	level=90
        cmd="/ir?hdmi=" + 8
    } else if (value>95) {
    	level=100
    }
	sendEvent(name: "level", value: level, isStateChange: true)
	runCmd(cmd)
}

def on() {
	log.debug "---ON COMMAND---"
	runCmd("=on")
}
def off() {
	runCmd("=off")
}
def tvinput() {
	runCmd("=input")
}
def chanup() {
	runCmd("=chanup")
}
def chandown() {
	runCmd("=chandown")
}
def tvprev() {
	runCmd("=prev")
}
def volup() {
	runCmd("=volup")
}
def voldown() {
	runCmd("=voldown")
}
def tvmute() {
	runCmd("=mute")
}

def runCmd(String varCommand) {
	def host = DeviceIP
	def hosthex = convertIPtoHex(host).toUpperCase()
	def LocalDevicePort = ''
	if (DevicePort==null) { LocalDevicePort = "80" } else { LocalDevicePort = DevicePort }
	def porthex = convertPortToHex(LocalDevicePort).toUpperCase()
	device.deviceNetworkId = "$hosthex:$porthex"
	def userpassascii = "${HTTPUser}:${HTTPPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()

	log.debug "The device id configured is: $device.deviceNetworkId"

	def path = DevicePath + varCommand
	log.debug "path is: $path"
	log.debug "Uses which method: $DevicePostGet"
	def body = DevicePath + varCommand
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

def parse(String description) {
//	log.debug "Parsing '${description}'"
	def whichTile = ''
	def map = [:]
	def retResult = []
	def descMap = parseDescriptionAsMap(description)
	def jsonlist = [:]
	def bodyReturned = ' '
	def headersReturned = ' '
	if (descMap["body"] && descMap["headers"]) {
		bodyReturned = new String(descMap["body"].decodeBase64())
		headersReturned = new String(descMap["headers"].decodeBase64())
	}
//	log.debug "BODY---" + bodyReturned
//	log.debug "HEADERS---" + headersReturned

	if (descMap["body"]) {
		if (headersReturned.contains("application/json")) {
			def body = new String(descMap["body"].decodeBase64())
			def slurper = new JsonSlurper()
			jsonlist = slurper.parseText(body)
			//log.debug "JSONLIST---" + jsonlist."CPU"
			jsonlist.put ("Date", new Date().format("yyyy-MM-dd h:mm:ss a", location.timeZone))
		} else if (headersReturned.contains("text/html")) {
			jsonlist.put ("Date", new Date().format("yyyy-MM-dd h:mm:ss a", location.timeZone))
			def data=bodyReturned.eachLine { line ->
			
				if (line.length()<15 && !line.contains("<") && !line.contains(">") && !line.contains("/")) {
					log.trace "---" + line
					if (line.contains('tv=')) { jsonlist.put ("tv", line.replace("tv=","")) }
					if (line.contains('hdmi=')) { jsonlist.put ("hdmi", line.replace("hdmi=","")) }
				}
			}
		}
	}
	if (descMap["body"] && (headersReturned.contains("application/json") || headersReturned.contains("text/html"))) {
		//putImageInS3(descMap)
		if (jsonlist."tv"=="on") {
			sendEvent(name: "switchon", value: "default", isStateChange: true)
			whichTile = 'mainon'
		}
		if (jsonlist."tv"=="off") {
			sendEvent(name: "switchoff", value: "default", isStateChange: true)
			whichTile = 'mainoff'
		}
		if (jsonlist."tv"=="input") {
			sendEvent(name: "tvinput", value: "default", isStateChange: true)
			whichTile = 'tvinput'
		}
		if (jsonlist."tv"=="chanup") {
			sendEvent(name: "chanup", value: "default", isStateChange: true)
			whichTile = 'chanup'
		}
		if (jsonlist."tv"=="chandown") {
			sendEvent(name: "chandown", value: "default", isStateChange: true)
			whichTile = 'chandown'
		}
		if (jsonlist."tv"=="prev") {
			sendEvent(name: "tvprev", value: "default", isStateChange: true)
			whichTile = 'tvprev'
		}
		if (jsonlist."tv"=="volup") {
			sendEvent(name: "volup", value: "default", isStateChange: true)
			whichTile = 'volup'
		}
		if (jsonlist."tv"=="voldown") {
			sendEvent(name: "voldown", value: "default", isStateChange: true)
			whichTile = 'voldown'
		}
		if (jsonlist."tv"=="mute") {
			sendEvent(name: "tvmute", value: "default", isStateChange: true)
			whichTile = 'tvmute'
		}
	}

	log.debug jsonlist

	//RESET THE DEVICE ID TO GENERIC/RANDOM NUMBER. THIS ALLOWS MULTIPLE DEVICES TO USE THE SAME ID/IP
	device.deviceNetworkId = "ID_WILL_BE_CHANGED_AT_RUNTIME_" + (Math.abs(new Random().nextInt()) % 99999 + 1)

	//CHANGE NAME TILE
	sendEvent(name: "displayName", value: DeviceIP, unit: "")

	//RETURN BUTTONS TO CORRECT STATE
	log.debug 'whichTile: ' + whichTile
    switch (whichTile) {
        case 'mainoff':
			//sendEvent(name: "mainswitch", value: "off", isStateChange: true)
			//def result = createEvent(name: "mainswitch", value: "off", isStateChange: true)
			sendEvent(name: "switch", value: "off", isStateChange: true)
			def result = createEvent(name: "switchon", value: "default", isStateChange: true)
			return result
        case 'mainon':
			//sendEvent(name: "mainswitch", value: "on", isStateChange: true)
			//def result = createEvent(name: "mainswitch", value: "on", isStateChange: true)
			sendEvent(name: "switch", value: "on", isStateChange: true)
			def result = createEvent(name: "switchon", value: "default", isStateChange: true)
			return result
        case 'tvinput':
			//sendEvent(name: "tvinput", value: "default", isStateChange: true)
			def result = createEvent(name: "tvinput", value: "default", isStateChange: true)
			return result
        case 'chanup':
			def result = createEvent(name: "chanup", value: "default", isStateChange: true)
			return result
        case 'chandown':
			def result = createEvent(name: "chandown", value: "default", isStateChange: true)
			return result
        case 'tvprev':
			//sendEvent(name: "tvprev", value: "default", isStateChange: true)
			def result = createEvent(name: "tvprev", value: "default", isStateChange: true)
			return result
        case 'volup':
			def result = createEvent(name: "volup", value: "default", isStateChange: true)
			return result
        case 'voldown':
			def result = createEvent(name: "voldown", value: "default", isStateChange: true)
			return result
        case 'tvmute':
			def result = createEvent(name: "tvmute", value: "default", isStateChange: true)
			return result
        case 'RebootNow':
			//sendEvent(name: "rebootnow", value: "default", isStateChange: true)
			def result = createEvent(name: "rebootnow", value: "default", isStateChange: true)
			return result
        // default:
			// sendEvent(name: "refreshswitch", value: "default", isStateChange: true)
			// def result = createEvent(name: "refreshswitch", value: "default", isStateChange: true)
			// // log.debug "refreshswitch returned ${result?.descriptionText}"
			// return result
    }
	
//	sendEvent(name: "switch", value: "on", unit: "")
//	sendEvent(name: "level", value: value, unit: "")
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