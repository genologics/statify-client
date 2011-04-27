#!/usr/bin/env python

import httplib
import argparse
import json

def main():
    parser = argparse.ArgumentParser(description='Log')
    parser.add_argument('host', metavar='host', help='host to connect to')
    parser.add_argument('guid', metavar='guid', help='guid token returned from prior registration')
    parser.add_argument('key', metavar='key', help='key')
    parser.add_argument('value', metavar='value', help='value')

    args = parser.parse_args()

    try:
        conn = httplib.HTTPConnection(args.host)
        conn.connect()
    except IOError as e:
        print "Unable to connect to '%s', reason: %s" % (args.host, str(e))
        return

    headers = {
        "Content-type": "application/json",
        "Accept": "application/json"
    }
    
    data = {
        "guid": args.guid,
        "key": args.key,
        "value": args.value
    }

    conn.request("POST", "/log", json.dumps(data), headers)
    response = conn.getresponse()
    print response.read()

if __name__ == "__main__":
    main()