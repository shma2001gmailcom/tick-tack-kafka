#!/usr/bin/env bash

ps aux | grep -i $1 | grep -v grep | awk '{print $2, $11}'