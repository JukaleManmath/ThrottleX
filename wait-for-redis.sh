#!/bin/sh

echo "⌛ Waiting for Redis to be ready at $1..."

until nc -z $1 6379; do
  >&2 echo "❌ Redis is unavailable - sleeping"
  sleep 1
done

echo "✅ Redis is up - starting app"
shift
exec "$@"