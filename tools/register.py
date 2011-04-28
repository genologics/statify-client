#!/usr/bin/env python

import httplib
import argparse

def main():
    parser = argparse.ArgumentParser(description='Register')
    parser.add_argument('host', metavar='host', help='host to connect to')

    args = parser.parse_args()

    try:
        conn = httplib.HTTPConnection(args.host)
        conn.connect()
    except IOError as e:
        print("Unable to connect to '%s', reason: %s" % (args.host, str(e)))
        return

    conn.request("GET", '/register')
    response = conn.getresponse()
    print("Registration GUID: %s" % response.read())

if __name__ == "__main__":
    main()