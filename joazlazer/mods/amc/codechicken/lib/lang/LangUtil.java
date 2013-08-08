package joazlazer.mods.amc.codechicken.lib.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Easy localisation access.
 */
public class LangUtil
{
    public static LangUtil instance = new LangUtil(null);
    
    public String prefix;
    
    public LangUtil(String prefix)
    {
        this.prefix = prefix;
    }
    
    public static String translateG(String s, Object... format)
    {
        return instance.translate(s, format);
    }
    
    public String translate(String s, Object... format)
    {
        if(prefix != null && !s.startsWith(prefix+"."))
            s = prefix+"."+s;
        String ret = LanguageRegistry.instance().getStringLocalization(s);
        if(ret.length() == 0)
            ret = LanguageRegistry.instance().getStringLocalization(s, "en_US");
        if(ret.length() == 0)
            ret = StatCollector.translateToLocal(s);
        if(ret.length() == 0)
            return s;
        if(format.length > 0)
            ret = String.format(ret, format);
        return ret;
    }

    public void addLangFile(InputStream resource, String lang) throws IOException
    {
        LanguageRegistry reg = LanguageRegistry.instance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
        while(true)
        {
            String read = reader.readLine();
            if(read == null)
                break;

            int equalIndex = read.indexOf('=');
            if(equalIndex == -1)
                continue;
            String key = read.substring(0, equalIndex);
            String value = read.substring(equalIndex+1);
            if(prefix != null)
                key = prefix+"."+key;
            reg.addStringLocalization(key, lang, value);
        }
    }
    
    public static LangUtil loadLangDir(String domain)
    {
        return new LangUtil(domain).addLangDir(new ResourceLocation(domain, "lang"));
    }
    
    public LangUtil addLangDir(ResourceLocation dir)
    {
        ResourceManager resManager = Minecraft.getMinecraft().func_110442_L();
        for(Language lang : (SortedSet<Language>)Minecraft.getMinecraft().func_135016_M().func_135040_d())
        {
            String langID = lang.func_135034_a();
            Resource langRes;
            try
            {
                langRes = resManager.func_110536_a(new ResourceLocation(dir.func_110624_b(), dir.func_110623_a()+'/'+langID+".lang"));
            }
            catch(Exception e)
            {
                continue;
            }
            try
            {
                addLangFile(langRes.func_110527_b(), langID);
            }
            catch(IOException e)
            {
                System.err.println("Failed to load lang resource. domain="+prefix+", resource="+langRes);
                e.printStackTrace();
            }
        }
        
        return this;
    }

    public static void loadBaseLangDir(ResourceLocation dir)
    {
        instance.addLangDir(dir);
    }
}
