# ContentProvider
This is essentially a practice run at implementing a content provider backed by a simple SQLite database in an Android application. I want to have my own example of a content provider so I can use it as a reference later. The idea is that this is a good place to learn and make mistakes, then I can take what I've learned and apply it to [Cash Caddy](https://github.com/justinrixx/Cash-Caddy). You're welcome to fork and use it for your own purposes!

## Database
If you care, the database is just two tables. The first is a list of makes of cars (e.g. Ford, Honda, etc) and the other is a list of cars. They look something like this:

Makes:
```
ID | Name
============
01 | Ford
02 | Toyota
03 | Honda
04 | Chevy
```

Cars:
```
ID | Make  | Year
=================
01 | Honda | 1965
02 | Ford  | 2001
03 | Chevy | 2011
```
