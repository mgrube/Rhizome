import web
import os

# This is a super basic tracker. 
# For now the idea is to connect every peer with 3 others.
# The method for this will be:
#	1. Receive POST from bootstrapper
# 	2. Add node and port to list of peers
#	3. Upon GET request, respond with 3 matches
#	4. Match unconnected nodes with each other 

urls = ( '/', 'index',
	'/tracker', 'tracker'
)

nodes = dict()

class index:
	def GET(self):
		return '''                                 ,_-=(!7(7/zs_.
                             .='  ' .`/,/!(=)Zm.
               .._,,._..  ,-`- `,\ ` -` -`dd7//WW.
          ,v=~/.-,-\- -!|V-s.)iT-|s|\-.'   `///mK%.
        v!`i!-.e]-g`bT/i(/[=.Z/m)K(YNYi..   /-]i44M.
      v`/,`|v]-DvLcfZ/eV/iDLN\D/ZK@%8W[Z..   `/d!Z8m
     //,c\(2(X/NYNY8]ZZ/bZd\()/d7WY%WKKW)   -'|(][%4.
   ,ddi\c(e)WX@WKKZKDKWMZ8(b5/ZK8]Z7%ffVM,   -.Y!bNMi
   /-iit5N)KWG%%8%%%%W8%ZWM(8YZvD)XN(@.  [   \]!/GXW[
  / ))G8\NMN%W%%%%%%%%%%8KK@WZKYK*ZG5KMi,-   vi[NZGM[
 i\!(44Y8K%8%%%**~YZYZ@%%%%%4KWZ/PKN)ZDZ7   c=//WZK%!
,dv\YtMZW8W%%f`,`.t/bNZZK%%W%%ZXb*K(K5DZ   -cdd/KM48
-|c5PbM4DDW%f  v./c\[tMY8W%PMW%D@KW)Gbf   -/(=ZZKM8[
2(N8YXWK85@K   -'c|K4/KKK%@  V%@@WD8e~  .//ct)8ZK%8`
=)b%]Nd)@KM[  !'\cG!iWYK%%|   !M@KZf    -c\))ZDKW%`
YYKWZGNM4/Pb  '-VscP4]b@W%     'Mf`   -L\///KM(%W!
!KKW4ZK/W7)Z. '/cttbY)DKW%     -`    .',)K(5KW%%f
'W)KWKZZg)Z2/,!/L(-DYYb54%  ,,`, -\-/v(((KK5WW%f
 \M4NDDKZZ(e!/vNTtZd)8\Mi!\-,-/i  -v((tKNGN%W%%
 'M8M88(Zd))///((dd|DtDYKK-`/-i(=)KtNNN@W%%%@%[
  !8%@KW5KKN4///s(\Pd!ROBY8/=2(/4ZdzKD%K%%%M8@%%
   '%%%W%dGNtPK(c\/2\[Z(ttNYZ2NZW8W8K%%%%YKM%M%%.
     *%%W%GW5@/%!e]_tZdY()v)ZXMZW%W%%%*5Y]K%ZK%8[
      '*%%%%8%8WK\)[/ZmZ/Zi]!/M%%%%@f\ \Y/NNMK%%!
        'VM%%%%W%WN5Z/Gt5/b)((cV@f`  - |cZbMKW%%|
           'V*M%%%WZ/ZGt5((+)L'-,,/  -)X(NWWff%%
                `~`MZ/DZGNZG5(((\,    ,t\i\Z)KW%
                   'M8K%8GN85(5///]i!v\K)85W%%f
                     YWWKKKKWZ8G54X/GGMeK@WM8%@
                      !M8%8%48WG@KWYbW%WWW%%%@
                        VM%WKWK%8K%%8WWWW%%%@`
                          ~*%%%%%%W%%%%%%%@~
                             ~*MM%%%%%%@f`

           
"Well, now that we have seen each other," said the Unicorn, 
"if you'll believe in me, I'll believe in you. Is that a bargain?"

'''
 
class tracker:
	def GET(self):
		if web.ctx.ip in nodes.keys():
			return '2'
		else:
			return '127.0.0.1:4040'
	def POST(self):
		i = web.input()
		if i.port != None:	
			nodes[web.ctx.ip] = i.port
			print 'Received info from ' + str(web.ctx.ip) + ' announcing port ' + str(i.port)
			return '2'
		else:
			return '1'

if __name__ == "__main__":
	app = web.application(urls, globals())
	app.run()
