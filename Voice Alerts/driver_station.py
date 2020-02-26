import os
import time
import threading
from networktables import NetworkTables

import logging # Required
logging.basicConfig(level=logging.DEBUG)
#ip = "roboRIO-1721-FRC"
#ip = "10.17.21.2"
ip = "localhost"
NetworkTables.initialize(server=ip)
table = NetworkTables.getTable("SmartDashboard")


class Queue():

    def __init__(self):
        self.queue = list() # Create the que list

    def enqueue(self, data):
        self.queue.insert(0, data) # Insert items into it

    def dequeue(self):
        if len(self.queue) > 0:
            return self.queue.pop()
        return None

def say_messages(queue):
    while True:
        message = queue.dequeue()
        if message != None:
            print(message)
            os.system('say.exe "' + message + "\"")

def get_messages(queue):
    previous_alert = ""
    while True:
        alert = table.getString("Alert", "Waiting for robot to connect.")
        if alert != previous_alert:
            #print(alert)
            queue.enqueue(alert)
        else:
            time.sleep(1)
        previous_alert = alert

if __name__ == "__main__":
    message_queue = Queue()

    message_queue.enqueue("[:phoneme on][dah<500,26>dah<180,14>dah<180,21>dah<500,19>dah<180,26>dah<500,21>]")
    message_queue.enqueue("Robot Voice Activated")

    say_messages_thread = threading.Thread(target=say_messages, args=(message_queue,))

    say_messages_thread.start()

    get_messages(message_queue)
