# 線上卡排遊戲
* * * 
# 遊戲發想動機
結合現今卡排遊戲---爐石，和兒時卡通片遊戲王中的情節而想到的。  

* * *
# 遊戲架構
* Server-Client
Server端主要功能是維護Players的配對，並擔任中間者，傳遞Players間的訊息封包。  
Client端主要負責遊戲中的計算，例如畫面的更新，判斷遊戲進行的階段。  
  
* 示意圖
![Game structure](http://i.imgur.com/GSTbFjl.png)
  
* A more detail about client side  
![Imgur](http://i.imgur.com/yFmU7DT.png)  

* * *
# 如何玩這個遊戲？
* Pre-required  
	+ Before you go to the next step,make sure you have
	+ installed "java ant" in your computer.

* Java ant install on Linux  

	sudo apt-get update  
	sudo apt-get install ant  

* Linux/Windows System user
	+ 1. git clone [this project link] [your folder]  
	+ 2. Go to your folder，and go to folder [your_folder_name]/[GameClient].  
	+ 3. type "make" in your terminal to compile the essential code first.  
	+ 4. then you can play the game...  
	+ " make client " to execute the code of client side(Player).
	+ " make server " to execute the server.  
 
* * *
# 遊戲執行畫面
* Server 需先執行（讓Client 端連上用的）  
> Linux: "ifconfig" 查詢IP  
> Windows: "ipconfig" 查詢IP  
![server](http://i.imgur.com/7NjbDfn.png)  

* Client 執行（遊戲主畫面)  
![main](http://i.imgur.com/K5B4k5K.png)

* 玩家設定
> 基本玩家設定  
> 1. 玩家頭貼  
> 2. 玩家ID  
> 3. 玩家性別  
![player-setting](http://i.imgur.com/8Leordt.png)

* 卡排選擇
> 選擇遊戲時，玩家擁有的三張角色卡牌  
![Choose-card](http://i.imgur.com/c1tuBaa.png)  

* 遊戲畫面
![Imgur](http://i.imgur.com/G2oiqW0.png)
* * *

# 共同開發者
* 陳殿善
* 林建宏
* 張頌宇
* 劉正仁
